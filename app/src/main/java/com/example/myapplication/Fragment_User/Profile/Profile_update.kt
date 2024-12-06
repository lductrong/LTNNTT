package com.example.myapplication.Fragment_User.Profile

import DatabaseHelper
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.R
import java.util.Calendar

class Profile_update : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edt_namecus: EditText
    private lateinit var edt_addresscus: EditText
    private lateinit var edt_gendercus: EditText
    private lateinit var edt_phonecus: EditText
    private lateinit var edt_note: EditText
    private lateinit var txt_calender: TextView
    private lateinit var btn_calender: Button
    private lateinit var btn_addcus: Button
    private var accountId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile_information)
        dbHelper = DatabaseHelper(this)
        edt_namecus = findViewById(R.id.edt_namecus)
        edt_addresscus = findViewById(R.id.edt_addresscus)
        edt_gendercus = findViewById(R.id.edt_gendercus)
        edt_phonecus = findViewById(R.id.edt_phonecus)
        edt_note = findViewById(R.id.edt_note)
        txt_calender = findViewById(R.id.txt_calender)
        btn_calender = findViewById(R.id.btn_calender)
        btn_addcus = findViewById(R.id.btn_addcus)

        accountId = intent.getStringExtra("account_id").toString()
        txt_calender.text = accountId
        if (intent.hasExtra("account_id")) {
            accountId = intent.getStringExtra("account_id").toString()
        } else {
            // Xử lý khi accountId không tồn tại
            Toast.makeText(this, "Không có accountId được truyền", Toast.LENGTH_SHORT).show()
            // Có thể kết thúc activity hoặc thực hiện các xử lý khác tùy vào yêu cầu của bạn
            finish()
        }

        btn_calender.setOnClickListener {
            openCalendar();
        }
        btn_addcus.setOnClickListener {
            addCustomer()
        }

    }

    private fun addCustomer() {
        val name = edt_namecus.text.toString()
        val address = edt_addresscus.text.toString()
        val gender = edt_gendercus.text.toString()
        val phone = edt_phonecus.text.toString()
        val note = edt_note.text.toString()
        val day = txt_calender.text.toString()


        if(name.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && phone.isNotEmpty() && note.isNotEmpty()  && day.isNotEmpty()){
            val customer = Model_customer("",name, address, day, gender, phone, note, accountId )
            val status = dbHelper.updateCustomerProfile(customer)
            if(status > -1){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                edt_namecus.text.clear()
                edt_addresscus.text.clear()
                edt_gendercus.text.clear()
                edt_phonecus.text.clear()
                edt_note.text.clear()
                txt_calender.setText("")
            }else{
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()

            }
        }else  {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCalendar() {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val calendar = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val text : String = ""+ dayOfMonth + "/"+ (month+1) + "/"+year
            txt_calender.setText(text)

        }, year, month, day)
        calendar.show()

    }
}