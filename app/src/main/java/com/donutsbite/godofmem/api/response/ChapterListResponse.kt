package com.donutsbite.godofmem.api.response

data class ChapterListResponse(
    val bookTitle: String,
    val chapters: List<ChapterResponse>
)

data class ChapterResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String?
)