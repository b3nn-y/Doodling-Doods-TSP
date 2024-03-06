package com.game.doodlingdoods.utils

import okhttp3.internal.and
import java.security.MessageDigest

class PasswordHash {
    fun hashPassword(password: String): String {

        val md = MessageDigest.getInstance("SHA-256")

        md.update(password.toByteArray())

        val bytes = md.digest()

        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(Integer.toString((bytes[i] and 0xff) + 0x100, 16).substring(1))
        }

        return sb.toString()
    }

}