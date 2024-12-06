package com.example.myapplication.Fragment_Admin.PhieuNhapStatus

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_Phieunhap_status
import com.example.myapplication.R

class Phieunhapstatus_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_PN_STATUS = "pn_status"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_phieunhapstatus)

        val edt_status: EditText = findViewById(R.id.edt_statuspn)
        val button_editstatus: Button = findViewById(R.id.btn_editpnstatus)
        val button_cancel: Button = findViewById(R.id.btncancel)
        val imgv_back: ImageView = findViewById(R.id.imgv_back)

        dbHelper = DatabaseHelper(this)
        val pnStatus = intent.getSerializableExtra(EXTRA_PN_STATUS) as Model_Phieunhap_status

        edt_status.setText(pnStatus.status)

        button_editstatus.setOnClickListener {
            val updatedPnStatus = Model_Phieunhap_status(
                pnstatusid = pnStatus.pnstatusid,
                status = edt_status.text.toString()
            )

            if (updatedPnStatus.status.isNotEmpty()) {
                dbHelper.updatepnStatus(updatedPnStatus, updatedPnStatus.pnstatusid)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PN_STATUS, updatedPnStatus)
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
