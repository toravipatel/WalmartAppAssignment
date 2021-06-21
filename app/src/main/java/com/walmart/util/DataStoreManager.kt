package com.walmart.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.walmart.util.Constants.Companion.IMAGE_DETAIL_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreManager @Inject constructor(val appContext: Context) {

    suspend fun storeImageDetail(imageDetails: String) {
        appContext.dataStore.edit { imageDetail ->
            imageDetail[IMAGE_DETAIL_KEY] = imageDetails
        }
    }

    suspend fun getImageDetail(): Flow<String> = appContext.dataStore.data.map {
            it.get(IMAGE_DETAIL_KEY).toString() ?: ""
        }

}

