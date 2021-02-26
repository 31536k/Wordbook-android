package com.donutsbite.godofmem.api.dto

data class QuestionRequest(
    val chapterId: Long,
    val readOnly: Boolean
)