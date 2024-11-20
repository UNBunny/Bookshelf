package com.example.bookshelf.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {

    data class Success(val booksItem: List<Book>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState

    val query: String
        get() = "trump"
}

class BookshelfViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    private val _uiStateBookshelf = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Loading)
    val uiStateBookshelf = _uiStateBookshelf.asStateFlow()


    init {
        getBooks(uiStateBookshelf.value.query)
    }

    fun getBooks(query: String = "") {
        viewModelScope.launch {
            _uiStateBookshelf.value = BookshelfUiState.Loading
            _uiStateBookshelf.value = try {
                val books = bookshelfRepository.getBooks(query)
                if (books == null) {
                    BookshelfUiState.Error
                } else if (books.isEmpty()) {
                    BookshelfUiState.Success(emptyList())
                } else {
                    BookshelfUiState.Success(books)
                }
            } catch (e: IOException) {
                e.printStackTrace() // Логирует ошибку в Logcat
                BookshelfUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace() // Логирует ошибку в Logcat
                BookshelfUiState.Error
            } catch (e: Exception) {
                e.printStackTrace() // Для отлова других исключений
                BookshelfUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}