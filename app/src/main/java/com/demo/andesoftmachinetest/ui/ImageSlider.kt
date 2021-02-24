package com.demo.andesoftmachinetest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.demo.andesoftmachinetest.R
import kotlinx.android.synthetic.main.image_slider_layout.*

class ImageSlider : DialogFragment() {

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val listOFBookImagesArgs: ImageSliderArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_slider_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
         dialog?.window?.setLayout(
             ViewGroup.LayoutParams.MATCH_PARENT,
             ViewGroup.LayoutParams.WRAP_CONTENT
         )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSliderAdapter = ImageSliderAdapter()
        val arrayListOfImages = listOFBookImagesArgs.listOfBookImages
        setAdapter(arrayListOfImages.toList())
    }

    private fun setAdapter(listOfData: List<String>) {
        val pageSnapHelper = PagerSnapHelper()
        pageSnapHelper?.attachToRecyclerView(rv_book_images)
        val arrayList = ArrayList(listOfData)
        imageSliderAdapter.addAll(arrayList)
        rv_book_images.adapter = imageSliderAdapter
    }
}