package com.donutsbite.godofmem.util

class LocalSettings {

    private val PREF: BaseSharedPreference = BaseSharedPreference("LocalSettings")

    companion object {
        val instance by lazy {
            LocalSettings()
        }
    }

    fun beginCommit() {
        PREF.beginCommit()
    }

    fun syncCommit() {
        PREF.syncCommit()
    }

    fun getAccessToken(): String {
        return PREF.getString(StringStore.accessToken)
    }

    fun setAccessToken(at: String) {
        PREF.commitString(StringStore.accessToken, at)
    }

    fun removeAccessToken() {
        PREF.remove(StringStore.accessToken)
    }

    fun getRefreshToken(): String {
        return PREF.getString(StringStore.refreshToken)
    }

    fun setRefreshToken(rt: String) {
        PREF.commitString(StringStore.refreshToken, rt)
    }

    fun setTokens(at: String, rt: String) {
        beginCommit()
        setAccessToken(at)
        setRefreshToken(rt)
        syncCommit()
    }

    fun removeRefreshToken() {
        PREF.remove(StringStore.refreshToken)
    }
}