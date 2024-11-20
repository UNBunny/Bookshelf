package com.example.bookshelf.di

import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.data.DefaultBookshelfRepository
import com.example.bookshelf.network.GoogleBooksApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer {
    val googleBooksApi: GoogleBooksApi
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer : AppContainer {
    override val googleBooksApi: GoogleBooksApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GoogleBooksApi.BASE_URL)
            .build()
            .create()
    }
    override val bookshelfRepository: BookshelfRepository by lazy {
        DefaultBookshelfRepository(googleBooksApi)
    }

}