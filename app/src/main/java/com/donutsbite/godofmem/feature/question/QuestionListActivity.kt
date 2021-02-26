package com.donutsbite.godofmem.feature.question

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Question
import com.donutsbite.godofmem.feature.quiz.QuizActivity
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil

class QuestionListActivity: AppCompatActivity() {
    private var chapterId: Long = 0
    private val questionListViewModel by viewModels<QuestionListViewModel> {
        QuestionListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionlist)

        chapterId = intent.getLongExtra(StringStore.chapterId, 0)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
                headerAdapter.updateWordCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.start_quiz)
        fab.setOnClickListener{
            fabOnClick()
        }

        questionListViewModel.requestQuestionsInChapter(chapterId, false)
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