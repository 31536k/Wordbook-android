package com.donutsbite.godofmem.feature.shared

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.feature.question.QuestionListActivity
import com.donutsbite.godofmem.util.StringStore

class SharedChapterListFragment: Fragment() {

    private val sharedChapterListViewModel by viewModels<SharedChapterListViewModel> {
        SharedChapterListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedChapterListViewModel.requestSharedChapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_shared_chapterlist, container, false)

        val chapterListAdapter = SharedChapterListAdapter {
            chapterAdapterOnClick(it)
        }

        val chapterRecyclerview: RecyclerView = view.findViewById(R.id.chapter_recycler_view)
        chapterRecyclerview.adapter = chapterListAdapter

        sharedChapterListViewModel.sharedChapterListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                chapterListAdapter.submitList(it as MutableList<Chapter>)
            }
        })

        return view
    }

    private fun chapterAdapterOnClick(chapter: Chapter) {
        val intent = Intent(activity, QuestionListActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapter.id)
        intent.putExtra(StringStore.readOnly, true)
        startActivity(intent)
    }
}