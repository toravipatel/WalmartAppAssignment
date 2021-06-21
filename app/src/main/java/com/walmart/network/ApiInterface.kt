package com.walmart.network

import com.walmart.model.ImageDetail
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    /**
     * function to get the ImageDetails
     * */
    @GET("planetary/apod?api_key=lI6oE3628JLWKXM53hegguG9gWocbL1qW2RqbAFX")
    fun getImageDetails() : Call<ImageDetail>
}