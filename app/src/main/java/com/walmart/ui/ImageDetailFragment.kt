package com.walmart.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.walmart.LoadingState
import com.walmart.R
import com.walmart.viemodel.ImageDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.country_list_fragment.*


@AndroidEntryPoint
class ImageDetailFragment : Fragment(R.layout.country_list_fragment) {

    private val imageDetailViewModel: ImageDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imageDetailViewModel.imageDetail.observe(viewLifecycleOwner) {

            titleValueTV.text = it.title
            descriptioValueTV.text = it.explanation
            activity?.let { it1 ->
                Glide
                    .with(it1)
                    .load(it.url)
                    .centerCrop()
                    .into(imageView)
            }
        }
        imageDetailViewModel.loadingState.observe(viewLifecycleOwner){
            when(it){

                LoadingState.SAVED_DATA_LOADING -> {
                    Toast.makeText(activity, "We are not connected to the internet, showing you the last image we have.",Toast.LENGTH_LONG).show()
                }
                LoadingState.LOADED ->{
                    Toast.makeText(activity, "We are not connected to the internet, showing you the last image we have.",Toast.LENGTH_LONG).show()

                }
                LoadingState.LOADING ->{
                    Toast.makeText(activity, "We are not connected to the internet, showing you the last image we have.",Toast.LENGTH_LONG).show()

                }

            }

        }
    }


}