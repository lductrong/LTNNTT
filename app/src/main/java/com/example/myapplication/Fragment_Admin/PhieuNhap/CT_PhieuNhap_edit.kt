package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.R

class CT_PhieuNhap_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var imgv_back: ImageView
    companion object {
        const val EXTRA_CT_PHIEUNHAP = "ct_phieunhap"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_ctphieunhap) // Ensure you have this layout file

        val etProductCode: EditText = findViewById(R.id.etProductCode)
        val edt_soLuong: EditText = findViewById(R.id.etQuantity)
        val edt_donGia: EditText = findViewById(R.id.etUnitPrice)
        val edt_thanhTien: EditText = findViewById(R.id.etTotal)
        val btn_save: Button = findViewById(R.id.btneditEntryDetail)

        dbHelper = DatabaseHelper(this)
        val ctPhieuNhap = intent.getSerializableExtra(EXTRA_CT_PHIEUNHAP) as Model_CTPhieuNhap

        edt_soLuong.setText(ctPhieuNhap.soLuong.toString())
        edt_donGia.setText(ctPhieuNhap.donGia.toString())
        edt_thanhTien.setText(ctPhieuNhap.thanhTien.toString())
        etProductCode.setText(ctPhieuNhap.maSp)
        imgv_back = findViewById(R.id.imgv_back)
        btn_save.setOnClickListener {
            val updatedCTPhieuNhap = Model_CTPhieuNhap(
                idctpn = ctPhieuNhap.idctpn,
                soLuong = edt_soLuong.text.toString().toIntOrNull() ?: 0,
                donGia = edt_donGia.text.toString().toDoubleOrNull() ?: 0.0,
                thanhTien = edt_thanhTien.text.toString().toDoubleOrNull() ?: 0.0,
                maSp = etProductCode.text.toString(),
                pnId = ctPhieuNhap.pnId
            )

            if (updatedCTPhieuNhap.soLuong > 0 && updatedCTPhieuNhap.donGia > 0 && updatedCTPhieuNhap.maSp.isNotEmpty()) {
                dbHelper.updateChiTietNhap(updatedCTPhieuNhap)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CT_PHIEUNHAP, updatedCTPhieuNhap)
                setResult(RESULT_UPDATED, resultIntent)
                finish()
            } else {
                showInvalidDataDialog()
            }
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
}
