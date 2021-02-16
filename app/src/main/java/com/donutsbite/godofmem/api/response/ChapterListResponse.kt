package com.donutsbite.godofmem.api.response

data class ChapterListResponse(
    val bookTitle: String,
    val chapters: List<ChapterResponse>
)

data class ChapterResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String?
)