package com.example.myapplication.Fragment_Admin.Dashboard

import DatabaseHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.R

class Fragment_dashboard_nam : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private  lateinit var edt_nam: EditText
    private  lateinit var btn_tongchinam: Button
    private  lateinit var txt_tongchinam: TextView
    private  lateinit var txt_tongthunam: TextView
    private  lateinit var txt_tongchinamtt: TextView
    private  lateinit var txt_tongthunamtt: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_dashboard_nam, container, false)
        edt_nam = view.findViewById(R.id.edt_nam)
        btn_tongchinam = view.findViewById(R.id.btn_tongchinam)
        txt_tongchinam = view.findViewById(R.id.txt_tongchinam)
        txt_tongthunam = view.findViewById(R.id.txt_tongthunam)
        txt_tongchinamtt = view.findViewById(R.id.txt_tongchinamtt)
        txt_tongthunamtt = view.findViewById(R.id.txt_tongthunamtt)
        dbHelper = DatabaseHelper(requireContext())

        btn_tongchinam.setOnClickListener {
            val nam = edt_nam.text.toString().toIntOrNull()?:0
            if(nam in 2000..2200){
                tongchinam(nam);
            }else{
                txt_tongchinam.setText("Dữ liệu không hợp lệ")
                txt_tongthunam.setText("Dữ liệu không hợp lệ")
            }
        }

        return view
    }

    private fun tongchinam(nam: Int) {
        val tongchinam = dbHelper.tongchitheonam(nam)
        val tongthunam = dbHelper.tongthutheonam(nam)
        val tongchinamtt = dbHelper.tongchitheonamthucte(nam)
        val tongthunamtt = dbHelper.tongthutheonamthucte(nam)

        txt_tongchinam.text = tongchinam.toString()
        txt_tongthunam.text = tongthunam.toString()
        txt_tongchinamtt.text = tongchinamtt.toString()
        txt_tongthunamtt.text = tongthunamtt.toString()
    }


}