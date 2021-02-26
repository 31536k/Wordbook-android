package com.donutsbite.godofmem.feature.question

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Question
import com.donutsbite.godofmem.feature.quiz.QuizActivity
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil

class QuestionListActivity : AppCompatActivity() {
    private var chapterId: Long = 0
    private var chapterTitle: String = "문제"

    private val questionListViewModel by viewModels<QuestionListViewModel> {
        QuestionListViewModelFactory()
    }
    private var readOnly = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionlist)

        chapterId = intent.getLongExtra(StringStore.chapterId, 0)
        chapterTitle = intent.getStringExtra(StringStore.chapterTitle)
        readOnly = intent.getBooleanExtra(StringStore.readOnly, true)

        setupToolbar()

        val headerAdapter =
            QuestionListHeaderAdapter()
        val questionListAdapter =
            QuestionListAdapter {
                adapterOnClick(it)
            }
        val concatAdapter = ConcatAdapter(headerAdapter, questionListAdapter)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.adapter = concatAdapter

        questionListViewModel.questionListLiveData.observe(this, Observer {
            it?.let {
                questionListAdapter.submitList(it as MutableList<Question>)
                headerAdapter.updateQuestionCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.start_quiz)
        if (readOnly) {
            fab.visibility = View.GONE
        } else {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener {
                fabOnClick()
            }
        }

        questionListViewModel.requestQuestionsInChapter(chapterId, false)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolbarTitleView: TextView = findViewById(R.id.toolbar_title)
        toolbarTitleView.text = chapterTitle
    }

    private fun adapterOnClick(question: Question) {
        ToastUtil.show("Question Asking - ${question.asking}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_question_list, menu)
        return true
    }

    private fun startQuizActivity() {
        val questionList = questionListViewModel.getCurrentQuestionList()
        if (questionList.isNullOrEmpty()) {
            return
        }

//        val intent = Intent(this, QuizActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable(
//            StringStore.questionsInChapterData,
//            QuestionsInChapter(chapterId, questionList)
//        )
//        intent.putExtra(StringStore.questionsInChapterBundle, bundle)

        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapterId)
        startActivity(intent)
    }

    private fun fabOnClick() {
        startQuizActivity()
    }
}