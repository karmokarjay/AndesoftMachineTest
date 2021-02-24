package com.demo.andesoftmachinetest.ui.addNewBookScreen

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorModel
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel
import com.demo.andesoftmachinetest.repository.viewModel.AuthorViewModel
import com.demo.andesoftmachinetest.repository.viewModel.BookRecordViewModel
import com.demo.andesoftmachinetest.utils.ApplicationConstants.Companion.GALLERY_PERMISSION_REQUEST_CODE
import com.demo.andesoftmachinetest.utils.ApplicationConstants.Companion.OPEN_GALLERY_REQUEST_CODE
import com.demo.andesoftmachinetest.utils.onDatePickerClick
import com.demo.andesoftmachinetest.utils.openSettingsForAcceptingPermissions
import com.demo.andesoftmachinetest.utils.showToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_new_book_record.*


class AddNewBookRecordFragment : Fragment(), View.OnClickListener {

    private lateinit var authorViewModel: AuthorViewModel
    private lateinit var bookRecordViewModel: BookRecordViewModel
    private var authorNameSelectedInDropDown: String = ""

    private var imageUriReceived: Uri? = null

    private var imageUriArrayList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authorViewModel = ViewModelProvider(this).get(AuthorViewModel::class.java)
        bookRecordViewModel = ViewModelProvider(this).get(BookRecordViewModel::class.java)
        imageUriArrayList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_book_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthorData()
        ed_doi_picker.setOnClickListener(this)
        btn_image_chooser.setOnClickListener(this)
        tv_submit_book_records.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_submit_book_records -> {
                val bookName = ed_book_name.text.trim().toString()
                val bookAuthorName = authorNameSelectedInDropDown
                val bookPrice = ed_book_price.text
                val bookDateOfIssue = ed_doi_picker.text.trim().toString()

                if (validationCheck(
                        bookName,
                        bookAuthorName,
                        bookPrice,
                        bookDateOfIssue,
                        imageUriArrayList
                    )
                ) {
                    val bookRecordViewModel = imageUriArrayList?.let {
                        BookRecordModel(
                            bookId = 0,
                            bookName = bookName,
                            bookAuthorName = bookAuthorName,
                            bookPrice = Integer.parseInt(bookPrice.toString()),
                            bookDateOfIssue = bookDateOfIssue,
                            bookThumbnailImageUriArrayList = it
                        )
                    }
                    bookRecordViewModel?.let { insertBookRecordDataInDB(it) }
                    requireContext().showToast("Book record submitted Successfully..!!")
                    findNavController().popBackStack()
                } else {
                    requireContext().showToast("Please fill out all the fields and images!!")
                }
            }
            R.id.btn_image_chooser -> {
                requestPermission()
            }
            R.id.ed_doi_picker -> {
                ed_doi_picker.onDatePickerClick(requireContext())
            }
        }
    }

    private fun insertBookRecordDataInDB(boolRecordModel: BookRecordModel) {
        bookRecordViewModel.addBookRecord(boolRecordModel)
    }

    private fun observeAuthorData() {
        authorViewModel.authorDataLiveData.observe(viewLifecycleOwner, Observer { authorList ->
            setAuthorSpinnerData(authorList)
            authorSpinnerClickEvent(authorList)
        })
    }

    private fun setAuthorSpinnerData(authorList: List<AuthorModel>) {
        val authorArray = arrayListOf<String>()
        for (authorItem in authorList) {
            authorArray.add(authorItem.authorName)
        }
        val authorArrayAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                authorArray
            )
        }
        author_name_spinner.adapter = authorArrayAdapter
    }

    private fun authorSpinnerClickEvent(authorList: List<AuthorModel>) {
        author_name_spinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    authorNameSelectedInDropDown = authorList.getOrNull(position)?.authorName ?: ""
                }
            }
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            GALLERY_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()) {
            for (permissionResult in grantResults) {
                if (permissionResult == PackageManager.PERMISSION_DENIED) {
                    activity?.let {
                        managePermissionDeniedFlow(this, add_new_book_record_parent_layout)
                    }
                    break
                } else if (permissionResult == PackageManager.PERMISSION_GRANTED && requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
                    pickMultipleImagesFromGallery()
                    break
                }
            }
        }
    }

    private fun managePermissionDeniedFlow(fragment: Fragment, view: View) {
        val shouldShowRequestPermissionPopup =
            fragment.shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
        if (!shouldShowRequestPermissionPopup) {
            val snackBar = Snackbar.make(
                view,
                "Allow  Permission",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction("Open settings") { requireContext().openSettingsForAcceptingPermissions() }
            snackBar.show()
        }
    }

    private fun pickMultipleImagesFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            OPEN_GALLERY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val clipData = data.clipData
                if (clipData?.itemCount != 0) {
                    imageUriArrayList?.clear()
                    val itemCount = clipData?.itemCount ?: 0
                    for (i in 0 until itemCount) {
                        val item: ClipData.Item? = clipData?.getItemAt(i)
                        val uri = item?.uri
                        uri?.let { imageUriArrayList?.add(it.toString()) }
                    }
                }
            }
            if (imageUriArrayList?.size ?: 0 > 0) {
                tv_images_selected_count.text =
                    resources.getString(R.string.images_selected_count_txt) + imageUriArrayList?.size.toString()
            }
        }
    }

    private fun validationCheck(
        bookName: String, authorName: String,
        bookPrice: Editable, bookDateOfIssue: String, imageUriArrayList: ArrayList<String>?
    ): Boolean {
        return when {
            bookName.isEmpty() -> false
            authorName.isEmpty() -> false
            bookPrice.isEmpty() -> false
            bookDateOfIssue.isEmpty() -> false
            imageUriArrayList?.size == 0 -> false
            else -> true
        }
    }
}