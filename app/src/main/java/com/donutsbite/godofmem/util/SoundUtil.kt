package com.donutsbite.godofmem.util

import android.media.AudioAttributes
import android.media.SoundPool
import com.donutsbite.godofmem.App.Companion.getApp
import com.donutsbite.godofmem.R


class SoundUtil {
    private val soundPoolMap = mutableMapOf<Int, Int>()

    private val soundPool: SoundPool

    companion object {
        val instance by lazy {
            SoundUtil()
        }

        val DONT_KNOW = 1
        val KNOW = 2
    }

    init {
        val context = getApp()
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        soundPoolMap[DONT_KNOW] = soundPool.load(context, R.raw.dont_know_sound, DONT_KNOW)
        soundPoolMap[KNOW] = soundPool.load(context, R.raw.know_sound, KNOW)
    }

    fun playSound(soundId: Int) {
        soundPoolMap[soundId]?.also {
            soundPool.play(it, 1f, 1f, 1, 0, 1f)
        }
    }
}