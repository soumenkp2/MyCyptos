package com.example.mycyptos.utils

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

class AppConstants {
    companion object {
        const val BASE_URL = "https://pro-api.coinmarketcap.com/"
        const val SHARED_PREF_NAME = "sp_branch"
        const val TOKEN = "token"
        const val API_KEY = "ff868612-5cfe-49f2-8e53-7fb6d5dc54fd"
        const val sort = "price"
        const val sort_dir = "desc"
        const val all_crypto_list_key = "top"
        const val first_crypto_key = "first"
        const val fav_crypto_list_key = "fav"

        fun snackBarTemplate(view: View, text: String): Snackbar {
            return Snackbar.make(view,text, Snackbar.LENGTH_INDEFINITE)
        }

    }
}