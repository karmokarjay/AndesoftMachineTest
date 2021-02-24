package com.demo.andesoftmachinetest.ui.homeScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorModel
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel
import com.demo.andesoftmachinetest.repository.viewModel.AuthorViewModel
import com.demo.andesoftmachinetest.repository.viewModel.BookRecordViewModel
import com.demo.andesoftmachinetest.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home_screen.*

class HomeScreenFragment : Fragment() {

    private lateinit var authorViewModel: AuthorViewModel
    private lateinit var bookRecordViewModel: BookRecordViewModel
    private lateinit var bookRecordArrayList: ArrayList<BookRecordModel>
    private lateinit var bookRecordsListingAdapter: BookRecordsListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authorViewModel = ViewModelProvider(this).get(AuthorViewModel::class.java)
        bookRecordViewModel = ViewModelProvider(this).get(BookRecordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookRecordsListingAdapter = BookRecordsListingAdapter()
        bookRecordArrayList = ArrayList()
        insertAuthorDataInDatabase()
        setAddRecordOnClickListener()
        observeBookRecordData()
    }

    private fun setAddRecordOnClickListener() {
        floating_btn_add_new_book_records.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_addNewBookRecord)
        }
    }

    private fun insertAuthorDataInDatabase() {
        val authorArrayList =
            arrayListOf(
                "George R.R. Martin",
                "J.K. Rowling",
                "Chetan Bhagat",
                "Vikram Chandra",
                "William Shakespeare",
                "Winston Groom",
                "Ben Mezrich",
                "Stephenie Meyer"
            )
        for (author in authorArrayList) {
            authorViewModel.addAuthor(AuthorModel(author))
        }
        Log.d("DBOperation: ", "AuthorDataAddedSuccessfully")
    }

    private fun observeBookRecordData() {
        bookRecordViewModel.bookRecordLiveData.observe(viewLifecycleOwner, { bookRecordList ->
            bookRecordArrayList = ArrayList()
            bookRecordArrayList.addAll(bookRecordList)
            setAdapter(bookRecordList)
        })
    }

    private fun setAdapter(listOfData: List<BookRecordModel>) {
        if (listOfData.size>0){
            tv_empty.visibility=View.GONE
        }else{
            tv_empty.visibility=View.VISIBLE
        }
        val arrayList = ArrayList(listOfData)
        bookRecordsListingAdapter.addAll(arrayList)
        rv_book_records.adapter = bookRecordsListingAdapter

        bookRecordsListingAdapter.itemClickCallbackWithPosAndData =
            object : BaseViewHolder.ItemClickedCallbackWithPositionAndData<BookRecordModel> {
                override fun onItemClickedWithPositionAndData(
                    position: Int,
                    data: BookRecordModel
                ) {
                    val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToImageSlider(
                        bookRecordArrayList[position].bookThumbnailImageUriArrayList.toTypedArray()
                    )
                    findNavController().navigate(action)
                }
            }
    }
}