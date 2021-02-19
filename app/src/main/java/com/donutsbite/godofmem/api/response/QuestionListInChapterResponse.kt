package com.donutsbite.godofmem.api.response

data class QuestionListInChapterResponse(
    val bookId: Long,
    val bookTitle: String,
    val chapterId: Long,
    val chapterTitle: String,
    val questions: List<QuestionResponse>
)

data class QuestionResponse(
    val id: Long,
    val asking: String,
    val answer: String,
    val wrongCount: Int
)