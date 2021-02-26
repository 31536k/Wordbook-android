package com.donutsbite.godofmem.domain

import com.donutsbite.godofmem.api.response.BookResponse

data class Book(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String?,
    var selected: Boolean
) {
    companion object {
        fun fromResponse(bookResponse: BookResponse) =
            Book(
                bookResponse.id,
                bookResponse.userId,
                bookResponse.title,
                bookResponse.description,
                false
            )
    }
}