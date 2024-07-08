package com.example.search

sealed interface SearchUiState {
    data object Default : SearchUiState
    data object Loading : SearchUiState
    data object Success : SearchUiState
    data class Error(val e: Throwable?) : SearchUiState
}