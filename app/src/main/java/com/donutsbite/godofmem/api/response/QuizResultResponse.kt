package com.donutsbite.godofmem.api.response

data class QuizResultResponse(
    val chapterId: Long,
    val allQuestions: List<QuestionResponse>,
    val knownQuestionIds: List<Long>,
    val unknownQuestionIds: List<Long>
)