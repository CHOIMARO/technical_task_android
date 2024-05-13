package com.choimaro.technical_task_android.util

import android.content.Context
import android.widget.Toast

class ToastManager(private val context: Context) {
    private var toast: Toast? = null

    fun showToast(message: String, duration: Int) {
        toast?.cancel()  // 이전 Toast 취소
        toast = Toast.makeText(context, message, duration).apply {
            show()
        }
    }
}