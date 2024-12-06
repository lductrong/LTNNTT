package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_OrderDetail
import com.example.myapplication.R

class Orderdetail_edit : AppCompatActivity() {

    companion object {
        const val EXTRA_ORDERDETAIL = "extra_orderdetail"
        const val RESULT_UPDATED = 1
    }

    private lateinit var edtQuantity: EditText
    private lateinit var edtTotal: EditText
    private lateinit var etmasp: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var orderDetail: Model_OrderDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_orderdetail)

        edtQuantity = findViewById(R.id.etsl)
        etmasp = findViewById(R.id.etmasp)
        edtTotal = findViewById(R.id.ettotal)
        btnSave = findViewById(R.id.btnAddEntryDetail)

        dbHelper = DatabaseHelper(this)

        // Get the order detail from the Intent
        orderDetail = intent.getSerializableExtra(EXTRA_ORDERDETAIL) as Model_OrderDetail

        // Set the current values to the EditTexts
        edtQuantity.setText(orderDetail.quantity.toString())
        etmasp.setText(orderDetail.productid.toString())
        edtTotal.setText(orderDetail.total.toString())

        btnSave.setOnClickListener {
            val updatedOrderDetail = Model_OrderDetail(
                orderdetailid = orderDetail.orderdetailid,
                orderid = orderDetail.orderid,
                productid = etmasp.text.toString(),
                quantity = edtQuantity.text.toString().toIntOrNull() ?: 0,
                total = edtTotal.text.toString().toDoubleOrNull() ?: 0.0
            )

            if (updatedOrderDetail.quantity > 0 && updatedOrderDetail.total > 0) {
                dbHelper.updateOrderDetail(updatedOrderDetail)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_ORDERDETAIL, updatedOrderDetail)
                setResult(RESULT_UPDATED, resultIntent)
                finish()
            } else {
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
