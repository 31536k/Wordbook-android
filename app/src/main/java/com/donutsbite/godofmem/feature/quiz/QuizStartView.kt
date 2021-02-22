package com.donutsbite.godofmem.feature.quiz

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.donutsbite.godofmem.R

class QuizStartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val dontKnowQuestionBtn: Button
    private val alllQuestionBtn: Button
    private val restartButton: Button

    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.quiz_start_view, this, true)
            .also {
                setBackgroundColor(resources.getColor(R.color.black_alpha_80, null))
                titleView = it.findViewById(R.id.title)
                dontKnowQuestionBtn = it.findViewById(R.id.dontknow_question_only)
                alllQuestionBtn = it.findViewById(R.id.all_question)
                restartButton = it.findViewById(R.id.restart)
            }
    }

    fun setUpDontKnowQuestionButton(onClick: () -> Unit) {
        dontKnowQuestionBtn.setOnClickListener { onClick() }
    }

    fun setDontKnowQuestionCount(questionCount: Int) {
        dontKnowQuestionBtn.text = "모르는 문제만\n($questionCount)"
    }

    fun setUpAllQuestionButton(onClick: () -> Unit) {
        alllQuestionBtn.setOnClickListener { onClick() }
    }

    fun setAllQuestionCount(questionCount: Int) {
        alllQuestionBtn.text = "전체 문제\n($questionCount)"
    }

    fun setUpRestartButton(onClick: () -> Unit) {
        restartButton.setOnClickListener { onClick() }
    }

    fun showAsStartMode() {
        titleView.text = "퀴즈를 시작합니다"
        dontKnowQuestionBtn.visibility = View.VISIBLE
        alllQuestionBtn.visibility = View.VISIBLE
        restartButton.visibility = View.GONE
    }

    fun showAsFinishMode() {
        titleView.text = "퀴즈가 완료되었습니다"
        dontKnowQuestionBtn.visibility = View.GONE
        alllQuestionBtn.visibility = View.GONE
        restartButton.visibility = View.VISIBLE
    }
}