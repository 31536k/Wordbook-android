package com.donutsbite.godofmem.feature.chapter.data

import com.donutsbite.godofmem.api.response.ChapterResponse

data class Chapter(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String?
) {
    companion object {
        fun fromResponse(chapterResponse: ChapterResponse) =
            Chapter(
                chapterResponse.id,
                chapterResponse.userId,
                chapterResponse.title,
                chapterResponse.description
            )
    }
}