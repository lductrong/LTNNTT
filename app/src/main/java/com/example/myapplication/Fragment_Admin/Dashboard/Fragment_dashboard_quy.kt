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


class Fragment_dashboard_quy : Fragment() {
    private lateinit var quySpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var txt_tongchiquy: TextView
    private lateinit var txt_tongthuquy: TextView
    private lateinit var txt_tongchiquythucte: TextView
    private lateinit var txt_tongthuquythucte: TextView
    private lateinit var btn_quy: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_dashboard_quy, container, false)
        quySpinner = view.findViewById(R.id.quySpinner)
        yearSpinner = view.findViewById(R.id.yearSpinner)
        btn_quy = view.findViewById(R.id.btn_quy)
        txt_tongchiquy = view.findViewById(R.id.txt_tongchiquy)
        txt_tongthuquy = view.findViewById(R.id.txt_tongthuquy)
        txt_tongchiquythucte = view.findViewById(R.id.txt_tongchiquythucte)
        txt_tongthuquythucte = view.findViewById(R.id.txt_tongthuquythucte)
        dbHelper = DatabaseHelper(requireContext())

        val quy = (1..4).map { it.toString() }
        val adapterquy = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, quy)
        adapterquy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quySpinner.adapter = adapterquy

        val years = (2020..2030).map { it.toString() }
        val adapteryears = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapteryears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = adapteryears
        btn_quy.setOnClickListener {
                val quy = quySpinner.selectedItem.toString().toIntOrNull() ?: 0
            val years = yearSpinner.selectedItem.toString().toIntOrNull() ?: 0
            if (quy in 1..4 && years in 2022..2030) {
                tongthuquy(quy, years)
            } else {
                showInvalidDataDialog()
            }
        }

        return view
    }

    private fun tongthuquy(quy: Int, nam: Int) {
        val tongchiquy = dbHelper.tongchitheoQuyNam(quy, nam)
        val tongthuquy = dbHelper.tongthutheoQuyNam(quy, nam)
        val tongchiquythucte = dbHelper.tongchitheoQuyNamthucte(quy, nam)
        val tongthuquythucte = dbHelper.tongthutheoQuyNamthucte(quy, nam)

        txt_tongchiquy.text = tongchiquy.toString()
        txt_tongthuquy.text = tongthuquy.toString()
        txt_tongthuquythucte.text = tongthuquythucte.toString()
        txt_tongchiquythucte.text = tongchiquythucte.toString()

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