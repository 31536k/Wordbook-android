package com.donutsbite.godofmem.feature.chapter

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.feature.question.QuestionListActivity
import com.donutsbite.godofmem.util.StringStore

class ChapterListFragment: Fragment() {

    private lateinit var drawer: DrawerLayout
    private lateinit var chapterListAdapter: ChapterListAdapter

    private val chapterListViewModel by viewModels<ChapterListViewModel> {
        ChapterListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chapterListViewModel.requestChaptersOfUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_chapterlist, container, false)

        chapterListAdapter = ChapterListAdapter {
            chapterAdapterOnClick(it)
        }

        val chapterRecyclerview: RecyclerView = view.findViewById(R.id.chapter_recycler_view)
        chapterRecyclerview.adapter = chapterListAdapter

        chapterListViewModel.chapterListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                chapterListAdapter.submitList(it as MutableList<Chapter>)
            }
        })

        return view
    }

    private fun chapterAdapterOnClick(chapter: Chapter) {
        val intent = Intent(activity, QuestionListActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapter.id)
        startActivity(intent)
    }

    fun filterWithBook(book: Book) {
        val chapterList = chapterListViewModel.chapterListLiveData.value
        if (book.id == -1L) {
            chapterListAdapter.submitList(chapterList)
        } else {
            chapterListAdapter.submitList(chapterList?.filter { it.bookId == book.id })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chapter_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START)
        }
        else if (item.itemId == R.id.reload) {
            chapterListViewModel.requestChaptersOfUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fabOnClick() {

    }
}