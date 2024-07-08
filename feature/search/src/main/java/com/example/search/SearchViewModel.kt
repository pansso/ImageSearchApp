package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.usecase.GetKakaoImageSearchUseCase
import com.example.model.ImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getKakaoImageSearchUseCase: GetKakaoImageSearchUseCase
) : ViewModel() {

    private val _searchText: MutableStateFlow<String> = MutableStateFlow<String>("")
    val searchText: StateFlow<String>
        get() = _searchText.asStateFlow()

    private val _searchResult: MutableStateFlow<PagingData<ImageData>> =
        MutableStateFlow<PagingData<ImageData>>(PagingData.empty())
    val searchResult: StateFlow<PagingData<ImageData>>
        get() = _searchResult.asStateFlow()

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow<SearchUiState>(SearchUiState.Default)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        debounceSearchText()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun debounceSearchText() {
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
                }.catch {
                    _uiState.value = SearchUiState.Error(it)
                    debounceSearchText()
                }.collect { it ->
                    _uiState.value = SearchUiState.Success
                    _searchResult.value = it
                }
        }
    }


    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun clearSearchText() {
        _searchText.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}