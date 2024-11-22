package com.example.myapplication.Fragment_Admin.Customer

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.R

class Customer_edit : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    companion object {
        const val EXTRA_CUSTOMER = "customer"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_customer)

        val edt_namecus: EditText = findViewById(R.id.edt_namecus)
        val edt_addresscus: EditText = findViewById(R.id.edt_addresscus)
        val txt_daycus: TextView = findViewById(R.id.txt_daycus)
        val edt_gender: EditText = findViewById(R.id.edt_gendercus)
        val edt_phonecus: EditText = findViewById(R.id.edt_phonecus)
        val edt_notecus: EditText = findViewById(R.id.edt_notecus)
        val button_editcus: Button = findViewById(R.id.button_editcus)
        val button_cancel: Button = findViewById(R.id.button_cancel)
        val imgv_back: ImageView = findViewById(R.id.imgv_back)

        dbHelper = DatabaseHelper(this)
        val customer = intent.getSerializableExtra(EXTRA_CUSTOMER) as Model_customer

        edt_namecus.setText(customer.namecus)
        edt_addresscus.setText(customer.addresscus)
        txt_daycus.setText(customer.daycus)
        edt_gender.setText(customer.gender)
        edt_phonecus.setText(customer.phonecus)
        edt_notecus.setText(customer.notecus)

        button_editcus.setOnClickListener {
            val updatedCustomer = Model_customer(
                customerid = customer.customerid,
                namecus = edt_namecus.text.toString(),
                addresscus = edt_addresscus.text.toString(),
                daycus = txt_daycus.text.toString(),
                gender = edt_gender.text.toString(),
                phonecus = edt_phonecus.text.toString(),
                notecus = edt_notecus.text.toString(),
                idaccount = customer.idaccount
            )

            if (updatedCustomer.namecus.isNotEmpty() && updatedCustomer.addresscus.isNotEmpty() &&
                updatedCustomer.daycus.isNotEmpty() && updatedCustomer.gender.isNotEmpty() &&
                updatedCustomer.phonecus.isNotEmpty() && updatedCustomer.notecus.isNotEmpty() &&
                updatedCustomer.idaccount.isNotEmpty()) {

                dbHelper.updateCustomer(updatedCustomer, updatedCustomer.customerid)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CUSTOMER, updatedCustomer)
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
