package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_Order
import com.example.myapplication.R

class Order_add: AppCompatActivity() {
    private lateinit var etaddress : EditText
    private lateinit var etdate : EditText
    private lateinit var ettinhtrang : EditText
    private lateinit var ettotal : EditText
    private lateinit var etidcus : EditText
    private lateinit var btnaddorder : Button
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this)
        setContentView(R.layout.fragment__order_add)
        etaddress = findViewById(R.id.etaddress)
        etdate = findViewById(R.id.etdate)
        ettinhtrang = findViewById(R.id.ettinhtrang)
        ettotal = findViewById(R.id.ettotal)
        etidcus = findViewById(R.id.etidcus)
        btnaddorder = findViewById(R.id.btnaddorder)
        btnaddorder.setOnClickListener {
            addOrder();
        }

    }

    private fun addOrder() {
        val address = etaddress.text.toString()
        val date = etdate.text.toString()
        val status = ettinhtrang.text.toString()
        val total = ettotal.text.toString().toDoubleOrNull()
        val idcus = etidcus.text.toString()

        if(address.isNotEmpty()&& date.isNotEmpty()&& status.isNotEmpty()&&total != null &&idcus.isNotEmpty()){
            val order = Model_Order("", date,idcus, total,address,status)
            val status =  dbHelper.addOrder(order)
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