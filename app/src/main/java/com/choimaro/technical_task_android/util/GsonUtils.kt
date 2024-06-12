package com.choimaro.technical_task_android.util

import android.net.Uri
import com.google.gson.Gson

object GsonUtils {
    fun toJson(value: Any?) : String {
        return Uri.encode(Gson().toJson(value))
    }

    inline fun <reified T> fromJson(value: String?) : T? {
        return runCatching {
            Gson().fromJson(value, T::class.java)
        }.getOrNull()
    }
}