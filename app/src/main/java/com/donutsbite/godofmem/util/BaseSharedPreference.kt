package com.donutsbite.godofmem.util

import android.content.Context
import android.content.SharedPreferences
import com.donutsbite.godofmem.App.Companion.getApp
import com.orhanobut.logger.Logger

open class BaseSharedPreference constructor(key: String) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    protected val context: Context = getApp()
    var isCommitting = false
        private set

    init {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun getEditor(): SharedPreferences.Editor {
        return if (isCommitting) editor else sharedPreferences.edit()
    }

    fun beginCommit() {
        Logger.i("beginCommit")
        isCommitting = true
    }

    fun syncCommit() {
        tryCommit(getEditor())
        isCommitting = false
        Logger.i("syncCommit")
    }

    fun syncCommitNow(): Boolean {
        val result = forceCommit(getEditor())
        isCommitting = false
        Logger.i("syncCommit")
        return result
    }

    private fun forceCommit(editor: SharedPreferences.Editor): Boolean {
        return editor.commit()
    }

    private fun tryCommit(edit: SharedPreferences.Editor) {
        edit.apply()
    }

    fun remove(key: String) {
        val edit = getEditor()
        edit.remove(key)
        tryCommit(edit)
        Logger.d("remove shared : key %s", key)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun commitString(key: String, value: String) {
        val edit = getEditor()
        edit.putString(key, value)
        if (!isCommitting) {
            tryCommit(edit)
        }
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun commitInt(key: String, value: Int) {
        val edit = getEditor()
        edit.putInt(key, value)
        if (!isCommitting) {
            tryCommit(edit)
        }
        Logger.d("commit shared : %s => %d", key, value)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun commitBoolean(key: String, value: Boolean) {
        val edit = getEditor()
        edit.putBoolean(key, value)
        if (!isCommitting) {
            tryCommit(edit)
        }
        Logger.d("commit shared : %s => %b", key, value)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun commitLong(key: String, value: Long) {
        val edit = getEditor()
        edit.putLong(key, value)
        if (!isCommitting) {
            tryCommit(edit)
        }
        Logger.d("commit shared : %s => %d", key, value)
    }
}