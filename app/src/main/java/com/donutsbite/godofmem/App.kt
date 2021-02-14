package com.donutsbite.godofmem

import android.app.Application
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this

        Stetho.initializeWithDefaults(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    companion object {
        @Volatile
        private lateinit var app: App

        @JvmStatic
        fun getApp(): App {
            return app
        }

        // content provider 등에서 app 변수 사용 전 확인용
        val isAppInitialized: Boolean
            get() = ::app.isInitialized
    }
}