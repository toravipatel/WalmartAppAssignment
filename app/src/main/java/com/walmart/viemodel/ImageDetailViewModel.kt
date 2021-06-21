package com.walmart.viemodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walmart.LoadingState
import com.walmart.model.ImageDetail
import com.walmart.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//ViewModel class to handle Image Related Details.
@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    //keep the loading status of the application
    var loadingState = MutableLiveData<LoadingState>()

    // Create a LiveData with a ImageDetail
    var imageDetail = MutableLiveData<ImageDetail>()

    init {
        fetchImageDetail()
    }

    /**
     * function to fetch image detail
     * */
    fun fetchImageDetail() {
        loadingState.value = LoadingState.LOADING
        imageDetail = imageRepository.getImageDetail()
        loadingState = imageRepository.getLoadingState()
    }
}