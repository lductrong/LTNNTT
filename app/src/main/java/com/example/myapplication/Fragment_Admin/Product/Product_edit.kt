package com.example.myapplication.Fragment_Admin.Product

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Product_edit : AppCompatActivity() {

        private lateinit var dbHelper: DatabaseHelper
        private lateinit var product: Model_product
        private var productId: Long = -1

        companion object {
            const val EXTRA_PRODUCT = "product"
            const val RESULT_UPDATED = 1
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment__product_edit)

            val editTextProductName: EditText = findViewById(R.id.editTextProductName)
            val editTextProductPrice: EditText = findViewById(R.id.editTextProductPrice)
            val editTextProductCategory: EditText = findViewById(R.id.editTextProductCategory)
            val editTextProductUnit: EditText = findViewById(R.id.editTextProductUnit)
            val editTextProductImage: EditText = findViewById(R.id.editTextProductImage)
            val buttonUpdateProduct: Button = findViewById(R.id.buttonUpdateProduct)

            dbHelper = DatabaseHelper(this)

            val product = intent.getSerializableExtra(EXTRA_PRODUCT) as Model_product
            productId = dbHelper.getProductId(product.masp) ?: -1

            editTextProductName.setText(product.tensp)
            editTextProductPrice.setText(product.gia.toString())
            editTextProductCategory.setText(product.maloai)
            editTextProductUnit.setText(product.donvi)
            editTextProductImage.setText(product.img)

            buttonUpdateProduct.setOnClickListener {
                val updatedProduct = Model_product(
                    masp = product.masp,
                    tensp = editTextProductName.text.toString(),
                    gia = editTextProductPrice.text.toString().toDouble(),
                    maloai = editTextProductCategory.text.toString(),
                    donvi = editTextProductUnit.text.toString(),
                    img = editTextProductImage.text.toString(),
                    productid = editTextProductName.text.toString()
                )

                if (productId.toInt() != -1) {
                    dbHelper.updateProduct(updatedProduct, productId)
                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_PRODUCT, updatedProduct)
                    setResult(RESULT_UPDATED, resultIntent)
                    finish()
                }else{
                    showInvalidDataDialog()
                }
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


}