package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_OrderDetail
import com.example.myapplication.R

class OrderDetail_add: AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private  lateinit var etmasp: EditText
    private  lateinit var etsl: EditText
    private  lateinit var ettotal: EditText
    private  lateinit var txtmapn: TextView
    private  lateinit var btnAddEntryDetail: Button
    private  lateinit var imgv_back: ImageView
    private var orderid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__orderdetails_add)
        dbHelper = DatabaseHelper(this)
        etmasp = findViewById(R.id.etmasp)
        etsl = findViewById(R.id.etsl)
        ettotal = findViewById(R.id.ettotal)
        btnAddEntryDetail = findViewById(R.id.btnAddEntryDetail)
        imgv_back = findViewById(R.id.imgv_back)
        txtmapn = findViewById(R.id.txtmapn)
        orderid = intent.getStringExtra("orderid").toString()
        txtmapn.text = orderid
        imgv_back.setOnClickListener {
            finish()
        }
        btnAddEntryDetail.setOnClickListener {
            addorderdetail()
        }
    }

    private fun addorderdetail() {
        val etmasp = etmasp.text.toString()
        val etsl = etsl.text.toString().toIntOrNull()
        val ettotal = ettotal.text.toString().toDoubleOrNull()

        if(etmasp.isNotEmpty() && etsl != null && ettotal != null){
            val orderdetail = Model_OrderDetail("",orderid, etmasp, etsl, ettotal)
            val status = dbHelper.addctdonhang(orderdetail)
            if(status > -1){
                Toast.makeText(this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Thêm dữ liệu không thành công", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}