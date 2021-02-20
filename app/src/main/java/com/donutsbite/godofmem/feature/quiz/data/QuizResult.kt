package com.donutsbite.godofmem.feature.quiz.data

import com.donutsbite.godofmem.api.response.QuizResultResponse
import com.donutsbite.godofmem.feature.question.data.Question

data class QuizResult(
    var chapterId: Long,
    val allQuestions: List<Question>,
    val knownQuestionIds: List<Long>,
    val unknownQuestionIds: List<Long>
) {
    companion object {
        fun fromResponse(res: QuizResultResponse) = QuizResult(
            chapterId = res.chapterId,
            allQuestions = res.allQuestions.map{Question.fromResponse(it)},
            knownQuestionIds = res.knownQuestionIds,
            unknownQuestionIds = res.unknownQuestionIds
        )
    }
}