package com.example.myapplication.Fragment_Admin.Customer

import DatabaseHelper
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.R
import java.util.Calendar

class Customer_add : AppCompatActivity() {
    private  lateinit var dbHelper: DatabaseHelper
    private lateinit var txt_daycus: TextView
    private lateinit var btncalander: ImageButton
    private lateinit var button_addcus: Button
    private lateinit var edt_namecus: EditText
    private lateinit var edt_addresscus: EditText
    private lateinit var edt_gendercus: EditText
    private lateinit var edt_phonecus: EditText
    private lateinit var edt_notecus: EditText
    private lateinit var imgv_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_customer_addcustomer)
        dbHelper = DatabaseHelper(this)
        btncalander =findViewById(R.id.button_calendar);
        txt_daycus = findViewById(R.id.txt_daycus)
        edt_namecus = findViewById(R.id.edt_namecus)
        edt_addresscus = findViewById(R.id.edt_addresscus)
        edt_gendercus = findViewById(R.id.edt_gendercus)
        edt_phonecus = findViewById(R.id.edt_phonecus)
        edt_notecus = findViewById(R.id.edt_notecus)
        button_addcus = findViewById(R.id.button_addcus)
        imgv_back = findViewById(R.id.imgv_back)
        btncalander.setOnClickListener(View.OnClickListener {
            openDatePicker();
        })
        button_addcus.setOnClickListener {
            addCustomer();
        }
        imgv_back.setOnClickListener {
            finish()
        }
    }

    private fun addCustomer() {
        val name = edt_namecus.text.toString();
        val address = edt_addresscus.text.toString();
        val gender = edt_gendercus.text.toString();
        val daycus = txt_daycus.text.toString()
        val phone = edt_phonecus.text.toString();
        val note = edt_notecus.text.toString();

        if(name.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && phone.isNotEmpty() && note.isNotEmpty()){
            val customer = Model_customer("",name,address,daycus,gender, phone, note,"");
            val status = dbHelper.addCustomer(customer)
            if(status > -1){
                Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                edt_namecus.text.clear()
                edt_addresscus.text.clear()
                txt_daycus.setText("")
                edt_gendercus.text.clear()
                edt_phonecus.text.clear()
                edt_notecus.text.clear()
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Thêm nhân viên không thành công", Toast.LENGTH_SHORT).show()
            }

        }else {
            Toast.makeText(this, "Vui lòng nhap day du thong tin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDatePicker() {
        val c = Calendar.getInstance();
        val day = c.get(Calendar.DAY_OF_MONTH);
        val month = c.get(Calendar.MONTH);
        val year = c.get(Calendar.YEAR);

        val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectcalander: String = ""+ dayOfMonth + "/" + (month+1) + "/" +year;
            txt_daycus.setText(selectcalander);
        }, year,month, day)
        datepicker.show();
    }

}