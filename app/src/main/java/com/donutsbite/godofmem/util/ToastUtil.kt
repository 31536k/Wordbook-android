package com.donutsbite.godofmem.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.donutsbite.godofmem.App
import com.donutsbite.godofmem.R

class ToastUtil {

    companion object {
        private val mainHandler = Handler(Looper.getMainLooper())

        fun show(
            @StringRes resId: Int,
            length: Int = Toast.LENGTH_SHORT,
            gravity: Int = Gravity.TOP
        ) {
            show(App.getApp().getString(resId), length, gravity)
        }

        fun show(
            message: CharSequence?,
            length: Int = Toast.LENGTH_SHORT,
            gravity: Int = Gravity.TOP
        ) {
            mainHandler
                .post { make(message, length, gravity).show() }
        }

        fun show(
            @StringRes resId: Int,
            length: Int = Toast.LENGTH_SHORT,
            gravity: Int = Gravity.TOP,
            delay: Long
        ) {
            mainHandler
                .postDelayed({ make(App.getApp().getString(resId), length, gravity).show() }, delay)
        }

        fun showImmediately(
            @StringRes messageId: Int,
            length: Int = Toast.LENGTH_SHORT,
            gravity: Int = Gravity.TOP
        ) {
            val message = App.getApp().getString(messageId)
            make(message, length, gravity).show()
        }

        @SuppressLint("ShowToast")
        fun make(
            message: CharSequence?,
            length: Int = Toast.LENGTH_SHORT,
            gravity: Int = Gravity.TOP
        ): Toast {
            val context: Context = App.getApp()
            val toast = Toast.makeText(context, message, length)
            toast.setGravity(
                gravity,
                0,
                context.resources.getDimensionPixelOffset(R.dimen.new_message_toast_padding)
            )
            return toast
        }
    }
}