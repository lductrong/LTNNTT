package com.example.myapplication.Fragment_Admin.Staff

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.Fragment_Admin.Product.Product_edit
import com.example.myapplication.R
class Staff_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_STAFF = "staff"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_staff)

        val edt_namestaff: EditText = findViewById(R.id.edt_namestaff)
        val edt_addressstaff: EditText = findViewById(R.id.edt_addressstaff)
        val txt_daystaff: TextView = findViewById(R.id.txt_daystaff)
        val edt_genderstaff: EditText = findViewById(R.id.edt_genderstaff)
        val edt_phonestaff: EditText = findViewById(R.id.edt_phonestaff)
        val edt_emailstaff: EditText = findViewById(R.id.edt_emailstaff)
        val txt_ngayvaolam: TextView = findViewById(R.id.txt_ngayvaolam)
        val edt_department: EditText = findViewById(R.id.edt_department)
        val edt_role: EditText = findViewById(R.id.edt_role)
        val button_editstaff: Button = findViewById(R.id.button_editstaff)
        val button_cancel: Button = findViewById(R.id.button_cancel)
        val imgv_back: ImageView = findViewById(R.id.imgv_back)

        dbHelper = DatabaseHelper(this)
        val staff = intent.getSerializableExtra(EXTRA_STAFF) as Model_staff

        edt_namestaff.setText(staff.tennv)
        edt_addressstaff.setText(staff.dcnv)
        txt_daystaff.setText(staff.nsnv)
        edt_genderstaff.setText(staff.gtnv)
        edt_phonestaff.setText(staff.sdtnv)
        edt_emailstaff.setText(staff.emailnv)
        txt_ngayvaolam.setText(staff.ngaylam)
        edt_department.setText(staff.pbanvn)
        edt_role.setText(staff.cvunv)

        button_editstaff.setOnClickListener {
            val updatedstaff = Model_staff(
                staffid = staff.staffid,
                tennv = edt_namestaff.text.toString(),
                gtnv = edt_genderstaff.text.toString(),
                nsnv = txt_daystaff.text.toString(),
                cvunv = edt_role.text.toString(),
                pbanvn = edt_department.text.toString(),
                ngaylam = txt_ngayvaolam.text.toString(),
                dcnv = edt_addressstaff.text.toString(),
                sdtnv = edt_phonestaff.text.toString(),
                emailnv = edt_emailstaff.text.toString()
            )

            if (updatedstaff.emailnv.isNotEmpty() && updatedstaff.tennv.isNotEmpty() && updatedstaff.gtnv.isNotEmpty() && updatedstaff.nsnv.isNotEmpty() && updatedstaff.cvunv.isNotEmpty()
                && updatedstaff.pbanvn.isNotEmpty() && updatedstaff.ngaylam.isNotEmpty() && updatedstaff.dcnv.isNotEmpty() && updatedstaff.sdtnv.isNotEmpty()) {
                dbHelper.updateStaff(updatedstaff, updatedstaff.staffid)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_STAFF, updatedstaff)
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
