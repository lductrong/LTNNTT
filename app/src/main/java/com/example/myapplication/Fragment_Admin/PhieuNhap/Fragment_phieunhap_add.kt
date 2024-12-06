package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.app.Activity
import android.app.DatePickerDialog
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_Phieunhap
import com.example.myapplication.R
import java.util.Calendar


class Fragment_phieunhap_add : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etEntryDate: EditText
    private lateinit var etManufacturer: EditText
    private lateinit var etTotalAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var edstatusid: Spinner
    private lateinit var btnAddEntry: Button
    private lateinit var btnctphieunhap: Button
    private lateinit var btncalendar: Button
    private lateinit var imgv_back: ImageView
    private var selectedCategoryId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__phieunhap_add)
        etEntryDate = findViewById(R.id.etEntryDate)
        etManufacturer = findViewById(R.id.etManufacturer)
        etTotalAmount = findViewById(R.id.etTotalAmount)
        etNote = findViewById(R.id.etNote)
        btnAddEntry = findViewById(R.id.btnAddEntry)
        btncalendar = findViewById(R.id.btncalendar)
        imgv_back = findViewById(R.id.imgv_back)
        edstatusid = findViewById(R.id.edstatusid)

        dbHelper = DatabaseHelper(this)
        btncalendar.setOnClickListener {
            openCalander();
        }

        btnAddEntry.setOnClickListener {
            addPhieunhap();
        }
        imgv_back.setOnClickListener {
            finish()
        }
        loadStatus()
    }
    private fun loadStatus() {
        val categories = dbHelper.getAllPNStatus()
        val categoryNames = categories.map { it.status }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edstatusid.adapter = adapter

        edstatusid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedCategoryId = categories[position].pnstatusid
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategoryId = null
            }
        }
    }
    private fun openCalander() {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val calander = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val txt = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            etEntryDate.setText(txt);
        }, year, month, day)
        calander.show()
    }

    private fun addPhieunhap() {
        val ngay = etEntryDate.text.toString()
        val nsx = etManufacturer.text.toString()
        val thanhtien = etTotalAmount.text.toString().toDoubleOrNull()
        val ghichu = etNote.text.toString()
        val idstatus = selectedCategoryId

        if(ngay.isNotEmpty() && nsx.isNotEmpty() && thanhtien != null && ghichu.isNotEmpty()){
            val phieunhap = Model_Phieunhap("",ngay, nsx,ghichu,thanhtien , idstatus.toString())
            val status = dbHelper.addPhieuNhap(phieunhap)
            if(status > -1){
                Toast.makeText(this, "Thêm phiếu nhập thành công", Toast.LENGTH_SHORT).show()
                etEntryDate.text.clear()
                etManufacturer.text.clear()
                etTotalAmount.text.clear()
                etNote.text.clear()
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Thêm phiếu nhập không thành công", Toast.LENGTH_SHORT).show()

            }

        }else{
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}