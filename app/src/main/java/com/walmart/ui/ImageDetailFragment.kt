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
import kotlinx.android.synthetic.main.image_detail_fragment.*


@AndroidEntryPoint
class ImageDetailFragment : Fragment(R.layout.image_detail_fragment) {

    private val imageDetailViewModel: ImageDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(activity, "We are not connected to the internet, showing you the last image we have.",Toast.LENGTH_LONG).show()


        imageDetailViewModel.imageDetail.observe(viewLifecycleOwner) {
            Toast.makeText(activity, "We are not connected to the internet, showing you the last image we have.",Toast.LENGTH_LONG).show()


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
                    showingOlderDataTV.visibility = View.VISIBLE
                    showingOlderDataTV.text = "We are not connected to the internet, showing you the last image we have."
                }
                LoadingState.LOADED ->{

                    showingOlderDataTV.visibility = View.GONE

                }
                LoadingState.LOADING ->{
                    showingOlderDataTV.visibility = View.GONE


                }

            }

        }
    }


}