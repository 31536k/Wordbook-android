package com.donutsbite.godofmem.feature.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.api.response.QuizResultResponse
import com.donutsbite.godofmem.domain.QuizResult

class QuizResultDataSource {
    private val quizResultLiveData = MutableLiveData<QuizResult>()

    fun getQuizResult(): LiveData<QuizResult> {
        return quizResultLiveData
    }

    fun requestQuizResult(chapterId: Long) {
        ApiLauncher.launchMain(
            { ApiService.instance.getQuizResult(chapterId) },
            { response -> setResult(response) }
        )
    }

    private fun setResult(quizResultResponse: QuizResultResponse) {
        quizResultLiveData.postValue(QuizResult.fromResponse(quizResultResponse))
//        val currentResult = quizResultLiveData.value
//        if (currentResult == null) {
//            quizResultLiveData.postValue(QuizResult.fromResponse(quizResultResponse))
//        } else {
//            qu
//        }
    }

    companion object {
        private var instance: QuizResultDataSource? = null

        fun getDataSource(): QuizResultDataSource {
            return synchronized(QuizResultDataSource::class) {
                val newInstance = instance
                    ?: QuizResultDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}