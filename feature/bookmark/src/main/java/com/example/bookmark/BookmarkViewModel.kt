package com.example.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.BookmarkUseCase
import com.example.model.BookmarkData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val _bookmarkList = MutableStateFlow<List<BookmarkData>>(emptyList())
    val bookmarkList: StateFlow<List<BookmarkData>>
        get() = _bookmarkList.asStateFlow()

    private val _searchResult = MutableStateFlow<List<BookmarkData>>(emptyList())
    val searchResult: StateFlow<List<BookmarkData>>
        get() = _searchResult.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)

    private val _displayList = MutableStateFlow<List<BookmarkData>>(emptyList())
    val displayList: StateFlow<List<BookmarkData>>
        get() = _displayList.asStateFlow()

    private val _uiState = MutableStateFlow<BookmarkUiState>(BookmarkUiState.Default)
    val uiState: StateFlow<BookmarkUiState>
        get() = _uiState.asStateFlow()

    private val _checkItems: MutableStateFlow<Set<String>> =
        MutableStateFlow<Set<String>>(emptySet())
    val checkItems: StateFlow<Set<String>>
        get() = _checkItems.asStateFlow()


    private var bookmarkJob: Job? = null

    init {
        refreshBookmarkList()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun debounceBookmarkText(searchText: Flow<String>) {
        bookmarkJob?.cancel()
        bookmarkJob = viewModelScope.launch {
            searchText
                .debounce(1000)
                .onEach { it ->
                    if (it.isBlank()) {
                        _isSearchActive.value = false
                        refreshBookmarkList()
                        _uiState.value = BookmarkUiState.Default
                    }
                }
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { text ->
                    _bookmarkList.map { list ->
                        list.filter { data ->
                            data.keyword.contains(text)
                        }
                    }
                }.flowOn(Dispatchers.Default)
                .catch { e ->
                    _uiState.value = BookmarkUiState.Error(e)
                }.collect { filterList ->
                    _uiState.value = BookmarkUiState.Success
                    _isSearchActive.value = true
                    _searchResult.value = filterList
                    refreshBookmarkList()
                }
        }
    }

    private fun getAllBookmarks() {
        viewModelScope.launch {
            bookmarkUseCase.getAllBookmarks().firstOrNull()?.let { list ->
                _bookmarkList.value = list
            }
        }
    }

    fun deleteBookmarkList(items: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkUseCase.deleteBookmarkUrlList(items)
            refreshBookmarkList()
            clearCheckItems()
        }
    }

    fun deleteBookmark(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkUseCase.deleteBookmarkUrl(url)
            refreshBookmarkList()
            _checkItems.update { it ->
                it - url
            }
        }

    }

    fun refreshBookmarkList() {
        getAllBookmarks()
        combineBookmarksAndSearch()
    }

    private fun combineBookmarksAndSearch() {
        viewModelScope.launch {
            combine(
                _bookmarkList,
                _searchResult,
                _isSearchActive
            ) { bookmarks, searchResults, isSearching ->
                if (isSearching) searchResults else bookmarks
            }.collect { list ->
                _displayList.value = list

                _checkItems.update { currentCheckItems ->
                    currentCheckItems.filter { checkedUrl ->
                        list.any { it.imageUrl == checkedUrl }
                    }.toSet()
                }
            }
            refreshBookmarkList()
        }
    }

    fun toggleCheckItem(url: String) {
        _checkItems.update { list ->
            if (url in list) {
                list - url
            } else {
                list + url
            }
        }
    }

    private fun clearCheckItems() {
        _checkItems.update { emptySet() }
    }

    override fun onCleared() {
        super.onCleared()
        bookmarkJob?.cancel()
    }
}