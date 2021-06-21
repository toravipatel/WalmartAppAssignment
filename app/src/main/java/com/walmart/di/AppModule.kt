package com.walmart.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.walmart.network.ApiInterface
import com.walmart.util.Constants
import com.walmart.util.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
    * Provides dependency of Retrofit
    * */

    @Singleton
    @Provides
    fun provideRetrofitService():Retrofit{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

        return retrofit
    }

    /**
   * Provides dependency of APIInterface
   * @param retrofit is the Retrofit
   * */
    @Singleton
    @Provides
    fun getAPIInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)


    /**
     * Provides dependency of DataStoreManager
     * @param retrofit is the Retrofit
     * */
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) =  DataStoreManager(context)


}