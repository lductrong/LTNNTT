package com.example.myapplication.Fragment_Admin.Order_Status

import DatabaseHelper
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_OrderStatus
import com.example.myapplication.R

class OrderStatus_add : AppCompatActivity(){
    private  lateinit var dbHelper: DatabaseHelper
    private  lateinit var edt_statusorder: EditText
    private  lateinit var btn_addorderstatus: Button
    private  lateinit var btncancel: Button
    private  lateinit var imgv_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__order_status_add)
        edt_statusorder = findViewById(R.id.edt_statusorder)
        btn_addorderstatus = findViewById(R.id.btn_addorderstatus)
        btncancel = findViewById(R.id.btncancel)
        imgv_back = findViewById(R.id.imgv_back)
        dbHelper = DatabaseHelper(this)

        btn_addorderstatus.setOnClickListener {
            addorderstatus();
        }
        imgv_back.setOnClickListener {
            finish()
        }

    }

    private fun addorderstatus() {
        val statusorder = edt_statusorder.text.toString()

        if( statusorder.isNotEmpty()){
            val orderStatus = Model_OrderStatus("",statusorder,)
            val status = dbHelper.addOrderstatus(orderStatus)
            if(status > -1 ){
                Toast.makeText(this, "Thêm trạng thái thành công", Toast.LENGTH_SHORT).show()
                edt_statusorder.text.clear()
                setResult(Activity.RESULT_OK)
                finish()
            }else {
                Toast.makeText(this, "Thêm trạng thái  không thành công", Toast.LENGTH_SHORT).show()
            }


        }else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }

}