package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_Order
import com.example.myapplication.R

class Order_edit : AppCompatActivity() {

    companion object {
        const val EXTRA_ORDER = "extra_order"
        const val RESULT_UPDATED = 1
    }

    private lateinit var edtOrderDay: EditText
    private lateinit var edtCustomerId: EditText
    private lateinit var edtTotalPrice: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtStatusId: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_order)

        edtOrderDay = findViewById(R.id.etdate)
        edtCustomerId = findViewById(R.id.etidcus)
        edtTotalPrice = findViewById(R.id.ettotal)
        edtAddress = findViewById(R.id.etaddress)
        edtStatusId = findViewById(R.id.ettinhtrang)
        btnSave = findViewById(R.id.btneditorder)

        dbHelper = DatabaseHelper(this)

        // Get the order details from the Intent
        val order = intent.getSerializableExtra(EXTRA_ORDER) as Model_Order

        // Set the current values to the EditTexts
        edtOrderDay.setText(order.orderday)
        edtCustomerId.setText(order.customerid)
        edtTotalPrice.setText(order.thanhtien.toString())
        edtAddress.setText(order.address)
        edtStatusId.setText(order.id_status)

        btnSave.setOnClickListener {
            val updatedOrder = Model_Order(
                order_id = order.order_id,
                orderday = edtOrderDay.text.toString(),
                customerid = edtCustomerId.text.toString(),
                thanhtien = edtTotalPrice.text.toString().toDoubleOrNull() ?: 0.0,
                address = edtAddress.text.toString(),
                id_status = edtStatusId.text.toString()
            )

            if (updatedOrder.order_id.isNotEmpty() && updatedOrder.orderday.isNotEmpty() && updatedOrder.customerid.isNotEmpty() &&
                updatedOrder.address.isNotEmpty() && updatedOrder.id_status.isNotEmpty() && updatedOrder.thanhtien > 0
            ) {
                dbHelper.updateOrder(updatedOrder, updatedOrder.order_id)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_ORDER, updatedOrder)
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
