package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.GoogleBooksApi

/**
 * Default Implementation of repository that retrieves volumes data from underlying data source.
 */
class DefaultBookshelfRepository(private val googleBooksApi: GoogleBooksApi) : BookshelfRepository {
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val res = googleBooksApi.getBooks(query)
            if (res.isSuccessful) {
                val body = res.body()
                if (body != null && !body.items.isNullOrEmpty()) {
                    body.items
                } else {
                    emptyList() // Логируем, если items отсутствует
                }
            } else {
                // Логируем код ошибки
                println("Error: ${res.code()} ${res.message()}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}