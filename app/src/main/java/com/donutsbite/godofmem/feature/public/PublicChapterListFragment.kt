package com.donutsbite.godofmem.feature.public

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

class PublicChapterListFragment: Fragment() {

    private val publicChapterListViewModel by viewModels<PublicChapterListViewModel> {
        PublicChapterListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        publicChapterListViewModel.requestPublicChapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_public_chapterlist, container, false)

        val chapterListAdapter = PublicChapterListAdapter {
            chapterAdapterOnClick(it)
        }

        val chapterRecyclerview: RecyclerView = view.findViewById(R.id.chapter_recycler_view)
        chapterRecyclerview.adapter = chapterListAdapter

        publicChapterListViewModel.publicChapterListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                chapterListAdapter.submitList(it as MutableList<Chapter>)
            }
        })

        return view
    }

    private fun chapterAdapterOnClick(chapter: Chapter) {
        val intent = Intent(activity, QuestionListActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapter.id)
        intent.putExtra(StringStore.chapterTitle, chapter.title)
        intent.putExtra(StringStore.readOnly, true)
        startActivity(intent)
    }
}