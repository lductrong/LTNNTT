package com.example.myapplication.Fragment_Admin.Order_Status

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_OrderStatus
import com.example.myapplication.R

class Orderstatus_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_ORDER_STATUS = "order_status"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_orderstatus)

        val edt_status: EditText = findViewById(R.id.edt_statusorder)
        val btn_editorderstatus: Button = findViewById(R.id.btn_editorderstatus)
        val button_cancel: Button = findViewById(R.id.btncancel)
        val imgv_back: ImageView = findViewById(R.id.imgv_back)

        dbHelper = DatabaseHelper(this)
        val orderStatus = intent.getSerializableExtra(EXTRA_ORDER_STATUS) as Model_OrderStatus

        edt_status.setText(orderStatus.status)

        btn_editorderstatus.setOnClickListener {
            val updatedOrderStatus = Model_OrderStatus(
                orderstatusid = orderStatus.orderstatusid,
                status = edt_status.text.toString()
            )

            if (updatedOrderStatus.status.isNotEmpty()) {
                dbHelper.updateorderStatus(updatedOrderStatus, updatedOrderStatus.orderstatusid)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_ORDER_STATUS, updatedOrderStatus)
                setResult(RESULT_UPDATED, resultIntent)
                finish()
            } else {
                showInvalidDataDialog()
            }
        }

        button_cancel.setOnClickListener {
            showDialog()
        }
        imgv_back.setOnClickListener {
            finish()
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
