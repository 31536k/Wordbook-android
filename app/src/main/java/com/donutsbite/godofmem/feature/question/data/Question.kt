package com.donutsbite.godofmem.feature.question.data

import com.donutsbite.godofmem.api.response.QuestionResponse

data class Question(
    val id: Long,
    val asking: String,
    val answer: String,
    val wrongCount: Int
) {
    companion object {
        fun fromResponse(questionResponse: QuestionResponse) =
            Question(
                questionResponse.id,
                questionResponse.asking,
                questionResponse.answer,
                questionResponse.wrongCount
            )
    }
}