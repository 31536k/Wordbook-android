package com.donutsbite.godofmem.feature.quiz.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.dto.QuizResultData
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.common.QuizType
import com.donutsbite.godofmem.feature.question.data.Question
import com.donutsbite.godofmem.util.LocalSettings
import com.donutsbite.godofmem.util.SoundUtil
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil
import com.orhanobut.logger.Logger

class QuizActivity: AppCompatActivity() {

    private var chapterId: Long = 0
    private val quizResultViewModel by viewModels<QuizResultViewModel> {
        QuizResultViewModelFactory()
    }

    private val quizType = QuizType.fromValue(LocalSettings.instance.getQuizType())
    private val soundUtil = SoundUtil.instance

    private val allQuestions = mutableListOf<Question>()
    private val quizQuestions = mutableListOf<Question>()
    private val initialKnownQuestionIds = mutableListOf<Long>()
    private val initialUnknownQuestionIds = mutableListOf<Long>()
    private val knownQuestionIds = mutableListOf<Long>()
    private val unknownQuestionIds = mutableListOf<Long>()
    private val actionLogs = mutableListOf<String>()
    private var currentIndex = 0
    private var answerVisible = false

    private var quizStarted = false
    private var quizFinished = false

    private lateinit var unknownCountView: TextView
    private lateinit var knownCountView: TextView
    private lateinit var quizStartView: QuizStartView
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var dontKnowBtn: Button
    private lateinit var knowBtn: Button
    private lateinit var undoBtn: ImageButton
    private lateinit var showAnswerBtn: Button
//    private lateinit var saveResultMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

//        intent?.extras?.getBundle(StringStore.questionsInChapterBundle)?.getParcelable<QuestionsInChapter>(StringStore.questionsInChapterData)?.also {
//            chapterId = it.chapterId
//            allQuestions.addAll(it.questionList)
//        }
        chapterId = intent.getLongExtra(StringStore.chapterId, 0)

        setupUI()

        quizResultViewModel.quizResultLiveData.observe(this, Observer {
            it?.also {
                allQuestions.clear()
                allQuestions.addAll(it.allQuestions)
                initialUnknownQuestionIds.clear()
                initialKnownQuestionIds.addAll(it.knownQuestionIds)
                initialUnknownQuestionIds.clear()
                initialUnknownQuestionIds.addAll(it.unknownQuestionIds)

                showQuizStartView()
            }
        })

        quizResultViewModel.requestQuizResult(chapterId)
    }

    private fun showQuizStartView() {
        if (initialKnownQuestionIds.size == 0 && initialUnknownQuestionIds.size == 0) {
            quizStartView.visibility = View.GONE
            startQuizWithAllQuestions()
        } else {
            quizStartView.showAsStartMode()
            quizStartView.setDontKnowQuestionCount(initialUnknownQuestionIds.size)
            quizStartView.setAllQuestionCount(allQuestions.size)
            quizStartView.visibility = View.VISIBLE
        }
    }

    private fun setupUI() {
        quizStartView = findViewById(R.id.quiz_start_view)
        quizStartView.visibility = View.GONE
        quizStartView.setUpRestartButton { restartQuiz() }
        quizStartView.setUpAllQuestionButton{ startQuizWithAllQuestions() }
        quizStartView.setUpDontKnowQuestionButton{ startQuizWithUnknownQuestions() }

        unknownCountView = findViewById(R.id.unknown_count)
        knownCountView = findViewById(R.id.known_count)
        questionTextView = findViewById(R.id.question_text)
        answerTextView = findViewById(R.id.answer_text)
        undoBtn = findViewById(R.id.undo_btn)
        dontKnowBtn = findViewById(R.id.dont_know_btn)
        knowBtn = findViewById(R.id.know_btn)
        showAnswerBtn = findViewById(R.id.show_answer_btn)

        dontKnowBtn.setOnClickListener { dontKnowThisQuestion() }
        knowBtn.setOnClickListener { knowThisQuestion() }
        showAnswerBtn.setOnClickListener { showAnswer() }
        undoBtn.setOnClickListener { unDo() }
    }

    private fun startQuizWithAllQuestions() {
        quizStartView.visibility = View.INVISIBLE
        quizQuestions.clear()
        quizQuestions.addAll(allQuestions)
        startQuiz()
    }

    private fun startQuizWithUnknownQuestions() {
        if (initialUnknownQuestionIds.isEmpty()) {
            ToastUtil.show("모르는 문제가 없습니다")
            return
        }
        quizStartView.visibility = View.INVISIBLE
        quizQuestions.clear()
        quizQuestions.addAll(allQuestions.filter {
            initialUnknownQuestionIds.contains(it.id)
        })
        startQuiz()
    }

    private fun startQuiz() {
        quizQuestions.shuffle()
        currentIndex = 0
        knownQuestionIds.clear()
        unknownQuestionIds.clear()
        actionLogs.clear()
        unknownCountView.text = "0"
        knownCountView.text = "0"
        showCurrentQuestion()
    }

    private fun showCurrentQuestion() {
        questionTextView.text = currentAskingOrAnswer()
        answerTextView.text = "---------"
    }

    private fun restartQuiz() {
        quizStarted = false;
        quizFinished = false;
        loadQuestionsFromQuizResult()
        showQuizStartView()
    }

    private fun currentAskingOrAnswer(): String {
        return if (quizType == QuizType.FIND_ANSWER) {
            quizQuestions[currentIndex].answer
        } else {
            quizQuestions[currentIndex].asking
        }
    }

    private fun showAnswer() {
        answerTextView.text = correctAnswer()
    }

    private fun correctAnswer(): String {
        return if (quizType == QuizType.FIND_ANSWER) {
            quizQuestions[currentIndex].asking
        } else {
            quizQuestions[currentIndex].answer
        }
    }

    private fun loadQuestionsFromQuizResult() {
        initialKnownQuestionIds.clear()
        initialKnownQuestionIds.addAll(knownQuestionIds)
        initialUnknownQuestionIds.clear()
        initialUnknownQuestionIds.addAll(unknownQuestionIds)
    }

    private fun dontKnowThisQuestion() {
        if (canAddCount()) {
            unknownQuestionIds.add(quizQuestions[this.currentIndex].id)
            actionLogs.add("unknown")
            unknownCountView.text = unknownQuestionIds.size.toString()
        }

        if (moveToNextQuestion()) {
            soundUtil.playSound(SoundUtil.DONT_KNOW)
        } else {
            this.onQuizFinished();
        }
    }

    private fun knowThisQuestion() {
        if (canAddCount()) {
            knownQuestionIds.add(quizQuestions[this.currentIndex].id)
            actionLogs.add("known");
            knownCountView.text = knownQuestionIds.size.toString()
        }

        if (moveToNextQuestion()) {
            soundUtil.playSound(SoundUtil.KNOW)
        } else {
            this.onQuizFinished();
        }
    }

    private fun canAddCount(): Boolean {
        return unknownQuestionIds.size + knownQuestionIds.size < quizQuestions.size
    }

    private fun moveToNextQuestion():Boolean {
        if (currentIndex >= quizQuestions.size - 1) {
            return false
        }
        answerVisible = false
        currentIndex += 1
        showCurrentQuestion()
        return true
    }

    private fun onQuizFinished() {
        quizFinished = true
        saveQuizResult()
        quizStartView.showAsFinishMode()
        quizStartView.visibility = View.VISIBLE
    }

    private fun saveQuizResult() {
        val quizResultData = QuizResultData(
            chapterId,
            knownQuestionIds = knownQuestionIds.joinToString(","),
            unknownQuestionIds = unknownQuestionIds.joinToString(",")
        )

        ApiLauncher.launchMain(
            { ApiService.instance.saveQuizResult(quizResultData) },
            { response -> Logger.i("quiz result saved") },
            { error -> ToastUtil.show("퀴즈 결과를 저장하지 못했습니다") }
        )
    }

    private fun unDo() {
        if (actionLogs.isNullOrEmpty()) {
            this.answerVisible = false;
            var targetIds = 0
            if (actionLogs.last() === "unknown") {
                unknownQuestionIds.removeAt(unknownQuestionIds.lastIndex)
            } else {
                knownQuestionIds.removeAt(knownQuestionIds.lastIndex)
            }
            actionLogs.removeAt(actionLogs.lastIndex)
            this.currentIndex -= 1;
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_quiz, menu)
//
//        saveResultMenuItem = menu.findItem(R.id.save_quiz)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.save_quiz) {
//            saveQuizResult()
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
}