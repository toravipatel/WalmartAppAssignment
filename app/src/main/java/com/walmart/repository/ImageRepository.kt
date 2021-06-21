package com.walmart.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.walmart.LoadingState
import com.walmart.model.ImageDetail
import com.walmart.network.ApiInterface
import com.walmart.util.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

//Provide details of Image either from network or local DB.
class ImageRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val dataStoreManager: DataStoreManager
) {

    val data = MutableLiveData<ImageDetail>()
    var loadingStateValue = MutableLiveData<LoadingState>()

    //function to get ImageDetail from network
    fun getImageDetail(): MutableLiveData<ImageDetail> {

        runBlocking {

            val lastSavedImageDetail = dataStoreManager.getImageDetail().first()
            val lastSavedImageDetailData: ImageDetail? =
                Gson().fromJson(lastSavedImageDetail, ImageDetail::class.java)
            val lastSavedate: String = lastSavedImageDetailData?.let {
                it.date
            } ?: kotlin.run {
                ""
            }

            //will get executed in case of loading data from local storage
            if (lastSavedImageDetailData != null && isDataValid(lastSavedate)) {
                data.postValue(lastSavedImageDetailData)
                loadingStateValue.postValue(LoadingState.SAVED_DATA_LOADING)
            } else {
                //Will get executed in case of fetching data from network
                apiInterface.getImageDetails().enqueue(object : Callback<ImageDetail> {
                    override fun onResponse(
                        call: Call<ImageDetail>,
                        response: Response<ImageDetail>
                    ) {
                        data.postValue(response.body())
                        loadingStateValue.postValue(LoadingState.LOADED)
                        runBlocking {
                            dataStoreManager.storeImageDetail(Gson().toJson(response.body()))
                        }
                    }

                    override fun onFailure(call: Call<ImageDetail>, t: Throwable) {
                        data.postValue(null)
                    }
                })
            }
        }
        return data
    }

    /**
    * function to check if cached data is valid or not
     * @param savedImageDate is the Date for saved image detail
     * @return true /false if it is older than 1 day
    * */
    private fun isDataValid(savedImageDate: String?): Boolean {

        if (savedImageDate.isNullOrEmpty())
            return false

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateString = simpleDateFormat.parse(savedImageDate)

        val diff: Long = Date().time - dateString.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return days < 1
    }

    /**
     * function will update the loading status of the app in case of calling image detail api
     * */
    fun getLoadingState(): MutableLiveData<LoadingState> {
        return loadingStateValue
    }
}