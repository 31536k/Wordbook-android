package com.donutsbite.godofmem.feature.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.domain.Question
import com.donutsbite.godofmem.util.ToastUtil

class QuestionDataSource {
    private val questionListLiveData = MutableLiveData<List<Question>>()

    fun getQuestionList(): LiveData<List<Question>> {
        return questionListLiveData
    }

    fun getCurrentQuestionList(): List<Question>? {
        return questionListLiveData.value
    }

    fun addQuestion(question: Question) {

    }

    fun addAllQuestion(questions: List<Question>) {
        val currentList = questionListLiveData.value
        if (currentList == null) {
            questionListLiveData.postValue(questions)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(0, questions)
            questionListLiveData.postValue(updatedList)
        }
    }

    fun setQuestions(questions: List<Question>) {
        questionListLiveData.postValue(questions)
    }

    private fun clearQuestions() {
        questionListLiveData.postValue(mutableListOf())
    }

    fun requestQuestionsInChapter(chapterId: Long) {
        clearQuestions()
        ApiLauncher.launchMain(
            { ApiService.instance.getQuestionsInChapter(chapterId) },
            { response -> addAllQuestion(response.questions.map{ Question.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다.")}
        )
    }

    companion object {
        private var instance: QuestionDataSource? = null

        fun getDataSource(): QuestionDataSource {
            return synchronized(QuestionDataSource::class) {
                val newInstance = instance
                    ?: QuestionDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}