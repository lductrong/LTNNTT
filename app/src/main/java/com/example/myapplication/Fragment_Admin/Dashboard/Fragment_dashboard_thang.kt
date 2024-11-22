package com.example.myapplication.Fragment_Admin.Dashboard

import DatabaseHelper
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.myapplication.R



class Fragment_dashboard_thang : Fragment(){
    private lateinit var txt_tongchithang: TextView
    private lateinit var txt_doanhthuthang: TextView
    private lateinit var txt_tongchithangthucte: TextView
    private lateinit var txt_tongdoanhthuthangthucte: TextView
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var btn_findday: Button
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_thang, container, false)
        btn_findday = view.findViewById(R.id.btn_findday)
        txt_tongchithang = view.findViewById(R.id.txt_tongchithang)
        txt_doanhthuthang = view.findViewById(R.id.txt_doanhthuthang)
        monthSpinner = view.findViewById(R.id.monthSpinner)
        yearSpinner = view.findViewById(R.id.yearSpinner)
        txt_tongdoanhthuthangthucte = view.findViewById(R.id.txt_tongdoanhthuthangthucte)
        txt_tongchithangthucte = view.findViewById(R.id.txt_tongchithangthucte)
        dbHelper = DatabaseHelper(requireContext())


        // Adapter for months
        val months = (1..12).map { it.toString() }
        val adaptermonths = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        adaptermonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = adaptermonths

        val years = (2020..2030).map { it.toString() }
        val adapteryears = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapteryears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = adapteryears


        btn_findday.setOnClickListener {
            val months = monthSpinner.selectedItem.toString().toIntOrNull() ?: 0
            val years = yearSpinner.selectedItem.toString().toIntOrNull() ?: 0
            if (months in 1..12 && years in 2022..2030) {
                tongthuchitheoThangNam(months, years)
            } else {
                showInvalidDataDialog()
            }
        }


        return view
    }
    private fun tongthuchitheoThangNam(month: Int, year: Int) {
        val tongchithang = dbHelper.tongchitheoThangNam(month, year)
        val tongdoanhthuthang = dbHelper.tongdoanhthutheoThangNam(month, year)
        val tongdoanhthuthangthucte = dbHelper.tongdoanhthutheoThangNamthucte(month, year)
        val tongchithangthucte = dbHelper.tongchitheoThangNamthucte(month, year)
        txt_tongchithang.text = tongchithang.toString()
        txt_doanhthuthang.text = tongdoanhthuthang.toString()
        txt_tongdoanhthuthangthucte.text = tongdoanhthuthangthucte.toString()
        txt_tongchithangthucte.text = tongchithangthucte.toString()
    }
    private fun showInvalidDataDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Thông báo")
        builder.setMessage("Dữ liệu không hợp lệ!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}