package com.donutsbite.godofmem.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.private.PrivateNoteFragment
import com.donutsbite.godofmem.feature.profile.ProfileFragment
import com.donutsbite.godofmem.feature.public.PublicNoteFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(PublicNoteFragment())
        fragments.add(PrivateNoteFragment())
        fragments.add(ProfileFragment())

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.public_note->setCurrentFragment(0)
                R.id.private_note->setCurrentFragment(1)
                R.id.profile->setCurrentFragment(2)
            }
            true
        }

        setCurrentFragment(1)
    }

    private fun setCurrentFragment(index: Int) {
        val fragment = fragments[index]
        bottomNavigationView.menu.getItem(index).isChecked = true
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()
        }
    }
}