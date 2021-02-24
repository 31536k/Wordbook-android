package com.donutsbite.godofmem.domain

import com.donutsbite.godofmem.api.response.QuizResultResponse

data class QuizResult(
    var chapterId: Long,
    val allQuestions: List<Question>,
    val knownQuestionIds: List<Long>,
    val unknownQuestionIds: List<Long>
) {
    companion object {
        fun fromResponse(res: QuizResultResponse) =
            QuizResult(
                chapterId = res.chapterId,
                allQuestions = res.allQuestions.map { Question.fromResponse(it) },
                knownQuestionIds = res.results.filter { it.know }.map { it.id },
                unknownQuestionIds = res.results.filter { !it.know }.map{ it.id }
            )
    }
}