package com.donutsbite.godofmem.api.dto

data class QuizResultData(
    val chapterId: Long,
    val knownQuestionIds: String,
    val unknownQuestionIds: String
)