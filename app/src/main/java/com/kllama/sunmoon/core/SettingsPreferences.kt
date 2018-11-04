package com.kllama.sunmoon.core

import android.content.Context
import android.content.SharedPreferences
import com.kllama.sunmoon.models.ShuttleType
import javax.inject.Inject

class SettingsPreferences @Inject constructor(context: Context) {

    val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_SETTINGS, Context.MODE_PRIVATE)
    }


    fun setDefaultPage(type: ShuttleType) {
        sharedPreferences.edit()
                .putInt(DEFAULT_PAGE, type.ordinal)
                .apply()
    }

    fun getDefaultPage(): ShuttleType {
        return ShuttleType.values()[sharedPreferences.getInt(DEFAULT_PAGE, 0)]
    }

    companion object {
        const val PREFERENCES_SETTINGS = "settings"

        const val DEFAULT_PAGE = "defaultPage"
    }
}