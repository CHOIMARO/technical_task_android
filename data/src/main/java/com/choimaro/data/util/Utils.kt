package com.choimaro.data.util

import java.security.MessageDigest

object Utils {
    const val BASE_URL = "https://dapi.kakao.com"
    fun generateHash(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}