package com.example.bookmark

sealed interface BookmarkUiState {
    data object Default : BookmarkUiState
    data object Loading : BookmarkUiState
    data object Success : BookmarkUiState
    data class Error(val e: Throwable?) : BookmarkUiState
}