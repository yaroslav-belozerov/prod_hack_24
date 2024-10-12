package com.yaabelozerov.holodos_mobile

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlin.reflect.KClass

sealed class C {
    companion object {
        val BASE_URL = "https://51.250.11.131"
        val IS_MOCK = true
    }
}

enum class Avatars(val res: Int) {
    CAT(R.drawable.avatar_cat), DOG(R.drawable.avatar_dog), FLOWER(R.drawable.avatar_flower), SNOWFLAKE(R.drawable.avatar_snowflake)
}