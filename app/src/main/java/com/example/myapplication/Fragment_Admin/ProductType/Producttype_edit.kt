package com.example.myapplication.Fragment_Admin.ProductType

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.R

class Producttype_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_PRODUCTTYPE = "producttype"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_producttype)

        val edtProductTypeName: EditText = findViewById(R.id.edt_producttype)
        val edtProductTypeImg: EditText = findViewById(R.id.edt_imgproducttype)
        val imgProductType: ImageView = findViewById(R.id.img_producttype)
        val btnUpdate: Button = findViewById(R.id.btn_addproducttype)
        val btnCancel: Button = findViewById(R.id.button2)

        dbHelper = DatabaseHelper(this)
        val productType = intent.getSerializableExtra(EXTRA_PRODUCTTYPE) as Model_producttype

        edtProductTypeName.setText(productType.tenlsp)
        edtProductTypeImg.setText(productType.imglsp)

        btnUpdate.setOnClickListener {
            val updatedProductType = Model_producttype(
                producttypeid = productType.producttypeid,
                tenlsp = edtProductTypeName.text.toString(),
                imglsp = edtProductTypeImg.text.toString()
            )

            if (updatedProductType.tenlsp.isNotEmpty() && updatedProductType.imglsp.isNotEmpty()) {
                dbHelper.updateProductType(updatedProductType,productType.producttypeid )
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_PRODUCTTYPE, updatedProductType)
                }
                setResult(RESULT_UPDATED, resultIntent)
                finish()
            } else {
                showInvalidDataDialog()
            }
        }

        btnCancel.setOnClickListener {
            showDialog()
        }
    }

    private fun showInvalidDataDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo")
        builder.setMessage("Dữ liệu không hợp lệ!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo")
        builder.setMessage("Bạn có muốn thoát quá trình sửa dữ liệu không")
        builder.setPositiveButton("Có") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.setNegativeButton("Không") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
