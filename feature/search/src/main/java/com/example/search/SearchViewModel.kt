package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.usecase.BookmarkUseCase
import com.example.domain.usecase.GetKakaoImageSearchUseCase
import com.example.model.BookmarkData
import com.example.model.ImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getKakaoImageSearchUseCase: GetKakaoImageSearchUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    /************************************************************************************
     * search
     ************************************************************************************/

    private val _searchResult: MutableStateFlow<PagingData<ImageData>> =
        MutableStateFlow(PagingData.empty())
    val searchResult: StateFlow<PagingData<ImageData>>
        get() = _searchResult.asStateFlow()

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Default)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private var searchJob: Job? = null

    /************************************************************************************
     * bookMark
     ************************************************************************************/

    private val _bookmarkList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val bookmarkList: StateFlow<List<String>>
        get() = _bookmarkList.asStateFlow()



    fun getAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkUseCase.getAllBookmarks().firstOrNull()?.let { list ->
                _bookmarkList.value = list.map { it.imageUrl }
            }
        }
    }

    fun toggledBookmark(url: String, keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (url in _bookmarkList.value) {
                bookmarkUseCase.deleteBookmarkUrl(url)
            } else {
                bookmarkUseCase.insertBookmark(BookmarkData(imageUrl = url, keyword = keyword))
            }
            getAllBookmarks()
        }

    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun debounceSearchText(searchText: Flow<String>) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            searchText
                .debounce(1000)
                .distinctUntilChanged()
                .onEach {
                    if (it.isBlank()) {
                        _searchResult.value = PagingData.empty()
                        _uiState.value = SearchUiState.Default
                    }
                }.filter {
                    it.isNotBlank()
                }.flatMapLatest { it ->
                    _uiState.value = SearchUiState.Loading
                    getKakaoImageSearchUseCase(query = it).cachedIn(viewModelScope)
                }.catch { error ->
                    _uiState.value = SearchUiState.Error(error)
                    _searchResult.value = PagingData.empty()
                }.collect { it ->
                    _uiState.value = SearchUiState.Success
                    _searchResult.value = it
                }
        }
    }


    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}