package com.example.bookshelf.ui.screens

import com.example.bookshelf.data.DefaultBookshelfRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope

sealed interface BookshelfUiState {

    data class Success(val bookItem: Book) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
    val query: String
        get() = "trump"
}
class BookshelfViewModel(private val bookshelfRepository: DefaultBookshelfRepository) {

    private val _uiStateBookshelf = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Loading)
    val uiStateBookshelf = _uiStateBookshelf.asStateFlow()


    init {
        getBooks()
    }

    fun getBooks(query : String = "") {
        viewModelScope.launch {

        }
    }

    companion object BookshelfViewModelFactory {

    }
}