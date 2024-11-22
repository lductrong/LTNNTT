package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_Phieunhap
import com.example.myapplication.R

class Phieunhap_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_PHIEUNHAP = "phieunhap"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_phieunhap) // Ensure you have this layout file

        val edt_ngayNhap: EditText = findViewById(R.id.etEntryDate)
        val edt_nsx: EditText = findViewById(R.id.etManufacturer)
        val edt_ghichu: EditText = findViewById(R.id.etNote)
        val edt_thanhTien: EditText = findViewById(R.id.etTotalAmount)
        val btn_save: Button = findViewById(R.id.btneditEntry)
        val imgv_back: ImageView = findViewById(R.id.imgv_back)

        dbHelper = DatabaseHelper(this)
        val phieunhap = intent.getSerializableExtra(EXTRA_PHIEUNHAP) as Model_Phieunhap

        edt_ngayNhap.setText(phieunhap.ngayNhap)
        edt_nsx.setText(phieunhap.nsx)
        edt_ghichu.setText(phieunhap.ghichu)
        edt_thanhTien.setText(phieunhap.thanhTien.toString())
        imgv_back.setOnClickListener {
            finish()
        }
        btn_save.setOnClickListener {
            val updatedPhieunhap = Model_Phieunhap(
                idpn = phieunhap.idpn,
                ngayNhap = edt_ngayNhap.text.toString(),
                nsx = edt_nsx.text.toString(),
                ghichu = edt_ghichu.text.toString(),
                thanhTien = edt_thanhTien.text.toString().toDoubleOrNull() ?: 0.0,
                idpnstatus = phieunhap.idpnstatus
            )

            if (updatedPhieunhap.ngayNhap.isNotEmpty() && updatedPhieunhap.thanhTien > 0) {
                dbHelper.updatePhieuNhap(updatedPhieunhap, updatedPhieunhap.idpn)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PHIEUNHAP, updatedPhieunhap)
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
