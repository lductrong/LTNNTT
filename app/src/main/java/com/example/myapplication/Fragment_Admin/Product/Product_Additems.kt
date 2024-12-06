package com.example.myapplication.Fragment_Admin.Product

import DatabaseHelper
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R
import java.io.IOException

class Product_Additems : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edt_masp: EditText
    private lateinit var edt_tensp: EditText
    private lateinit var edt_gia: EditText
    private lateinit var edt_maloai: Spinner
    private lateinit var edt_donvi: EditText
    private lateinit var imageGalleryView: ImageView
    private lateinit var imgv_back: ImageView
    private lateinit var edt_imageSource: EditText
    private lateinit var buttonAddProduct: Button
    private lateinit var buttonAdd: Button
    private val MY_REQUEST_CODE = 10
    private val TAG: String = Product_Additems::class.java.name
    private var selectedCategoryId: String? = null
    private val mActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result: ActivityResult ->
            Log.e(TAG, "onActivityResult")
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val uri = data?.data
                try {
                    if (uri != null) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver, uri
                        )
                        imageGalleryView.setImageBitmap(bitmap)
                        edt_imageSource.setText(uri.toString())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__product_additems)
        dbHelper = DatabaseHelper(this)
        buttonAdd =findViewById(R.id.btn_selectimage)
        buttonAddProduct = findViewById(R.id.button_addproduct)
        edt_masp = findViewById(R.id.edt_masp)
        edt_tensp = findViewById(R.id.edt_tensp)
        edt_gia = findViewById(R.id.edt_gia)
        edt_maloai = findViewById(R.id.edt_maloai)
        edt_donvi = findViewById(R.id.edt_donvi)
        imgv_back = findViewById(R.id.imgv_back)
        edt_imageSource = findViewById(R.id.edt_image) // Ensure correct reference
        imageGalleryView = findViewById(R.id.image_gallery)

        buttonAdd.setOnClickListener {
            onClickRequestPermission()
        }
        buttonAddProduct.setOnClickListener(View.OnClickListener {
            addProduct();
        })
        imgv_back.setOnClickListener {
            finish()
        }
        loadCategoriesIntoSpinner()
    }


    private fun loadCategoriesIntoSpinner() {
        val categories = dbHelper.getAllProductType()
        val categoryNames = categories.map { it.tenlsp }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_maloai.adapter = adapter

        edt_maloai.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedCategoryId = categories[position].producttypeid
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategoryId = null
            }
        }
    }

    private fun addProduct() {
        val masp = edt_masp.text.toString()
        val tensp = edt_tensp.text.toString()
        val gia = edt_gia.text.toString().toDoubleOrNull()
        val maloai = selectedCategoryId
        val donvi = edt_donvi.text.toString()
        val image = edt_imageSource.text.toString()

        if (masp.isNotEmpty() && tensp.isNotEmpty() && gia != null && maloai != null && donvi.isNotEmpty() && image.isNotEmpty()) {
            val product = Model_product("",masp, tensp, gia, maloai, donvi, image)
            val status = dbHelper.addProduct(product)
            if (status > -1) {
                Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show()
                // Clear the inputs
                edt_masp.text.clear()
                edt_tensp.text.clear()
                edt_gia.text.clear()
                edt_donvi.text.clear()
                edt_imageSource.text.clear()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }
}
