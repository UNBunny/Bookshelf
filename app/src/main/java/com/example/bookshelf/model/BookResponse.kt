package com.example.bookshelf.model

import android.provider.MediaStore.Images.Thumbnails
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val description: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val description: String,
    val imageLinks: ImageLinks?
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)