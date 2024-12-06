package com.example.myapplication.Fragment_Admin.Staff

import DatabaseHelper
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.R
import java.util.Calendar

class Staff_addstaff : AppCompatActivity() {

    private lateinit var button_calendarstaff: ImageButton;
    private lateinit var dbHelper: DatabaseHelper;
    private lateinit var button_calendarngayvaolam: ImageButton;
    private lateinit var button_addstaff: Button;
    private lateinit var txt_daystaff: TextView;
    private lateinit var txt_ngayvaolam: TextView;
    private lateinit var edt_namestaff: EditText;
    private lateinit var edt_addressstaff: EditText;
    private lateinit var edt_genderstaff: EditText;
    private lateinit var edt_phonestaff: EditText;
    private lateinit var edt_emailstaff: EditText;
    private lateinit var edt_department: EditText;
    private lateinit var edt_role: EditText;
    private lateinit var imgv_back: ImageView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_staff_addstaff)
        dbHelper = DatabaseHelper(this)
        button_calendarstaff = findViewById(R.id.button_calendarstaff)
        button_calendarngayvaolam = findViewById(R.id.button_calendarngayvaolam)
        txt_daystaff = findViewById(R.id.txt_daystaff)
        txt_ngayvaolam = findViewById(R.id.txt_ngayvaolam)
        button_addstaff = findViewById(R.id.button_addstaff)
        edt_namestaff= findViewById(R.id.edt_namestaff)
        edt_addressstaff= findViewById(R.id.edt_addressstaff)
        edt_genderstaff= findViewById(R.id.edt_genderstaff)
        edt_phonestaff= findViewById(R.id.edt_phonestaff)
        edt_emailstaff= findViewById(R.id.edt_emailstaff)
        edt_department= findViewById(R.id.edt_department)
        imgv_back= findViewById(R.id.imgv_back)
        edt_role= findViewById(R.id.edt_role)

        button_calendarstaff.setOnClickListener(View.OnClickListener {
            opencalendarstaff();
        })

        button_calendarngayvaolam.setOnClickListener(View.OnClickListener {
            opencalendarvaolam();
        })

        button_addstaff.setOnClickListener{
            addStaff();
        }
        imgv_back.setOnClickListener {
            finish()
        }
    }

    private fun addStaff() {
        val tennv = edt_namestaff.text.toString();
        val dc = edt_addressstaff.text.toString();
        val ns = txt_daystaff.text.toString();

        val gt = edt_genderstaff.text.toString()
        val sdt = edt_phonestaff.text.toString();
        val email = edt_emailstaff.text.toString();
        val ngaylam = txt_ngayvaolam.text.toString();
        val phongban = edt_department.text.toString();
        val cvu = edt_role.text.toString();
        if(tennv.isNotEmpty() && dc.isNotEmpty()&& ns.isNotEmpty() && gt.isNotEmpty() && sdt.isNotEmpty()&& email.isNotEmpty()&& ngaylam.isNotEmpty()&& phongban.isNotEmpty()&& cvu.isNotEmpty()){
            val staff = Model_staff("",tennv,gt, ns,cvu,phongban ,ngaylam,dc,sdt,email)
            val status = dbHelper.addStaff(staff)
            if(status > -1){
                Toast.makeText(this,"Them thành công",Toast.LENGTH_SHORT).show();
                edt_namestaff.text.clear();
                edt_addressstaff.text.clear();
                txt_daystaff.text = ""
                edt_phonestaff.text.clear();
                edt_emailstaff.text.clear();
                txt_ngayvaolam.text = ""
                edt_department.text.clear();
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Thêm nhân viên không thành công", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }

    }

    private fun opencalendarvaolam() {
        val c = Calendar.getInstance();
        val day = c.get(Calendar.DAY_OF_MONTH);
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val datevaolam = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val txtvaolam = ""+dayOfMonth+ "/"+(month +1)+"/"+year;
            txt_ngayvaolam.setText(txtvaolam);
        },day,month,year);

        datevaolam.show();

    }

    private fun opencalendarstaff() {
        val c = Calendar.getInstance();
        val day = c.get(Calendar.DAY_OF_MONTH);
        val month = c.get(Calendar.MONTH);
        val year = c.get(Calendar.YEAR)

        val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val txtns : String = "" + dayOfMonth + "/" + (month +1) + "/" + year;
            txt_daystaff.setText(txtns);
        },day,month,year)

        datepicker.show()
    }


}