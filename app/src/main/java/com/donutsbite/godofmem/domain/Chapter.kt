package com.donutsbite.godofmem.domain

import com.donutsbite.godofmem.api.response.ChapterResponse

data class Chapter(
    val id: Long,
    val bookId: Long,
    val title: String,
    val description: String?
) {
    companion object {
        fun fromResponse(chapterResponse: ChapterResponse) =
            Chapter(
                chapterResponse.id,
                chapterResponse.bookId,
                chapterResponse.title,
                chapterResponse.description
            )
    }
}