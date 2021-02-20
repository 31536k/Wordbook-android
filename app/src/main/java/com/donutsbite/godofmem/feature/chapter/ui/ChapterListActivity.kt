package com.donutsbite.godofmem.feature.chapter.ui

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
import com.donutsbite.godofmem.feature.chapter.data.Chapter
import com.donutsbite.godofmem.feature.question.ui.QuestionListActivity
import com.donutsbite.godofmem.util.StringStore

class ChapterListActivity: AppCompatActivity() {

    private var bookId: Long = 0
    private val chapterListViewModel by viewModels<ChapterListViewModel> {
        ChapterListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapterlist)

        bookId = intent.getLongExtra(StringStore.bookId, 0)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val headerAdapter = ChapterListHeaderAdapter()
        val chapterListAdapter = ChapterListAdapter{adapterOnClick(it)}
        val concatAdapter = ConcatAdapter(headerAdapter, chapterListAdapter)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.adapter = concatAdapter

        chapterListViewModel.chapterListLiveData.observe(this, Observer {
            it?.let {
                chapterListAdapter.submitList(it as MutableList<Chapter>)
                headerAdapter.updateBookCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.addchapter_fab)
        fab.setOnClickListener{
            fabOnClick()
        }

        chapterListViewModel.requestChaptersOfBook(bookId = bookId)
    }

    private fun adapterOnClick(chapter: Chapter) {
        val intent = Intent(this, QuestionListActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapter.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_chapter_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.reload) {
            chapterListViewModel.requestChaptersOfBook(bookId)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fabOnClick() {

    }
}