package com.example.myapplication.Fragment_Admin.PhieuNhapStatus

import DatabaseHelper
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.Data.Model.Model_Phieunhap_status
import com.example.myapplication.R

class PhieuNhapStatus_add :AppCompatActivity() {
    private  lateinit var dbHelper: DatabaseHelper

    private  lateinit var edt_statuspn: EditText
    private  lateinit var btn_addpnstatus: Button
    private  lateinit var btncancel: Button
    private  lateinit var imgv_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment__phieu_nhap_status_add)

        edt_statuspn = findViewById(R.id.edt_statuspn)
        btn_addpnstatus = findViewById(R.id.btn_addpnstatus)
        btncancel = findViewById(R.id.btncancel)
        imgv_back = findViewById(R.id.imgv_back)
        dbHelper = DatabaseHelper(this)

        btn_addpnstatus.setOnClickListener {
            addpnstatus();
        }
        imgv_back.setOnClickListener {
            finish()
        }
    }
    private fun addpnstatus() {
        val statuspn = edt_statuspn.text.toString()

        if(statuspn.isNotEmpty()){
            val pnStatus = Model_Phieunhap_status("",statuspn)
            val status = dbHelper.addPNstatus(pnStatus)
            if(status > -1 ){
                Toast.makeText(this, "Thêm trạng thái thành công", Toast.LENGTH_SHORT).show()
                edt_statuspn.text.clear()
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