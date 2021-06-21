package com.walmart.util

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

interface Constants {

    companion object{
        const val  BASE_URL = "https://api.nasa.gov/"
        val IMAGE_DETAIL_KEY = stringPreferencesKey("example_counter")
    }
}