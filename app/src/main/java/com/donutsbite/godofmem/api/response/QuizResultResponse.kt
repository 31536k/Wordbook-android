package com.donutsbite.godofmem.api.response

data class QuizResultResponse(
    val chapterId: Long,
    val allQuestions: List<QuestionResponse>,
    val results: List<QuestionResultResponse>
)

data class QuestionResultResponse(
    val id: Long, // question id
    val know: Boolean,
    val wrongCount: Int
)