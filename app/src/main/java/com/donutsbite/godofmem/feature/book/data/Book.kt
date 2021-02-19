package com.donutsbite.godofmem.feature.book.data

import com.donutsbite.godofmem.api.response.BookResponse

data class Book(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String?
) {
    companion object {
        fun fromResponse(bookResponse: BookResponse) =
            Book(
                bookResponse.id,
                bookResponse.userId,
                bookResponse.title,
                bookResponse.description
            )
    }
}