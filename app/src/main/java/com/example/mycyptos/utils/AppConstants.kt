package com.example.mycyptos.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class AppConstants {
    companion object {
        const val BASE_URL = "https://android-messaging.branch.co/"
        const val SHARED_PREF_NAME = "sp_branch"
        const val TOKEN = "token"
        const val API_KEY = "ff868612-5cfe-49f2-8e53-7fb6d5dc54fd"

        fun snackBarTemplate(view: View, text: String): Snackbar {
            return Snackbar.make(view,text, Snackbar.LENGTH_INDEFINITE)
        }
    }
}