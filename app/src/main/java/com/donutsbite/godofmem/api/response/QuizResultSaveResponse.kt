package com.donutsbite.godofmem.api.response

data class QuizResultSaveResponse(
    val chapterId: Long,
    val knownQuestionIds: List<Long>,
    val unknownQuestionIds: List<Long>
)