package com.example.myapplication.Fragment_Admin.ProductType

import DatabaseHelper
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.R
import java.io.IOException

class Producttype_Addtype : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edt_producttype : EditText
    private lateinit var edt_imgproducttype : EditText
    private lateinit var btn_addproducttype : Button
    private lateinit var btn_openfile : Button
    private lateinit var img_producttype : ImageView
    private lateinit var imgv_back : ImageView
    private val MY_REQUEST_CODE = 10
    private val TAG: String = Producttype_Addtype::class.java.name

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
                        img_producttype.setImageBitmap(bitmap)
                        edt_imgproducttype.setText(uri.toString())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__producttype__addtype)
        edt_producttype = findViewById(R.id.edt_producttype)
        edt_imgproducttype = findViewById(R.id.edt_imgproducttype)
        btn_addproducttype = findViewById(R.id.btn_addproducttype)
        btn_openfile = findViewById(R.id.btn_openfile)
        img_producttype = findViewById(R.id.img_producttype)
        imgv_back = findViewById(R.id.imgv_back)
        btn_openfile.setOnClickListener {
            onClickRequestPermission()
        }

        dbHelper = DatabaseHelper(this)
        btn_addproducttype.setOnClickListener {
            addLoaisp();
        }
        imgv_back.setOnClickListener {
            finish()
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
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun addLoaisp() {
        val tenloaisp = edt_producttype.text.toString()
        val imglsp = edt_imgproducttype.text.toString()
        if(tenloaisp.isNotEmpty()){
            val producttype = Model_producttype("",tenloaisp, imglsp)
            val status = dbHelper.addLoaiSP(producttype)
            if(status > -1){
                Toast.makeText(this, "THêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                edt_producttype.text.clear()
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Thêm sản phẩm không thành công", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this,"VUi lòng nhậd day du thong tin", Toast.LENGTH_SHORT).show()
        }
    }

}