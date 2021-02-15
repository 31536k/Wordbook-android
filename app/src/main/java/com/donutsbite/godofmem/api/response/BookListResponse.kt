package com.donutsbite.godofmem.api.response

data class BookListResponse(
    val books: List<BookResponse>
)

data class BookResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String?
)