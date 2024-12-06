package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.graphics.ColorSpace.Model
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.R
class Fragment_ctphieunhap_add : AppCompatActivity() {
    private  lateinit var dbHelper: DatabaseHelper
    private lateinit var txtmapn:TextView
    private lateinit var spnsp:Spinner
    private lateinit var etQuantity:TextView
    private lateinit var etUnitPrice:TextView
    private lateinit var etTotal:TextView
    private lateinit var btnAddEntryDetail:Button
    private lateinit var btncalendar:Button
    private lateinit var imgv_back: ImageView
    private var selectedId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ctphieunhap_add)

        txtmapn = findViewById(R.id.txtmapn)
        spnsp = findViewById(R.id.spnsp)
        etQuantity = findViewById(R.id.etQuantity)
        etUnitPrice = findViewById(R.id.etUnitPrice)
        etTotal = findViewById(R.id.etTotal)
        imgv_back = findViewById(R.id.imgv_back)
        btnAddEntryDetail = findViewById(R.id.btnAddEntryDetail)

        dbHelper = DatabaseHelper(this)

        val mapn = intent.getStringExtra("mapn")
        txtmapn.text = mapn

        btnAddEntryDetail.setOnClickListener {
            addCtphieunhap();
        }
        imgv_back.setOnClickListener {
            finish()
        }
        loadProduct()
    }
    private fun loadProduct() {
        val categories = dbHelper.getAllproduct()
        val categoryNames = categories.map { it.tensp }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnsp.adapter = adapter

        spnsp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedId = categories[position].productid
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedId = null
            }
        }
    }
    private fun addCtphieunhap() {
        val mapn = txtmapn.text.toString()
        val soluong = etQuantity.text.toString().toIntOrNull()
        val dongia = etUnitPrice.text.toString().toDoubleOrNull()
        val tongtien = etTotal.text.toString().toDoubleOrNull()
        val masp = selectedId

        if(mapn.isNotEmpty()  && soluong != null && dongia != null && tongtien != null ){
            val ctpn = Model_CTPhieuNhap("",soluong, dongia,tongtien, masp.toString(),mapn )
            val status = dbHelper.addChiTietNhap(ctpn)
            if(status > -1){
                Toast.makeText(this, "Thêm ct phiếu nhập thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Thêm ct phiếu nhập  không thành công", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Điền đayà đủ thông tin", Toast.LENGTH_SHORT).show()
        }

    }
}