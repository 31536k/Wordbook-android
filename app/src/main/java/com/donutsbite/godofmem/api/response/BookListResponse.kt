package com.donutsbite.godofmem.api.response

data class BookListResponse(
    val books: List<BookResponse>
)

data class BookResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String?
)