package com.donutsbite.godofmem.feature.question.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.question.data.Question
import com.donutsbite.godofmem.feature.quiz.ui.QuizActivity
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil

class QuestionListActivity: AppCompatActivity() {
    private var chapterId: Long = 0
    private val questionListViewModel by viewModels<QuestionListViewModel> {
        QuestionListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapterlist)

        chapterId = intent.getLongExtra(StringStore.chapterId, 0)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val headerAdapter = QuestionListHeaderAdapter()
        val questionListAdapter = QuestionListAdapter{adapterOnClick(it)}
        val concatAdapter = ConcatAdapter(headerAdapter, questionListAdapter)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.adapter = concatAdapter

        questionListViewModel.questionListLiveData.observe(this, Observer {
            it?.let {
                questionListAdapter.submitList(it as MutableList<Question>)
                headerAdapter.updateWordCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.addchapter_fab)
        fab.setOnClickListener{
            fabOnClick()
        }

        questionListViewModel.requestWordsInChapter(chapterId)
    }

    private fun adapterOnClick(question: Question) {
        ToastUtil.show("Question Asking - ${question.asking}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_question_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.start_quiz) {
            startQuizActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startQuizActivity() {
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    private fun fabOnClick() {

    }
}