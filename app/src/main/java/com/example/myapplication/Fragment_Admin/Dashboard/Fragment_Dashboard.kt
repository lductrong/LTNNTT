package com.example.myapplication.Fragment_Admin.Dashboard

import DatabaseHelper
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R


class Fragment_Dashboard : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var txt_tongdoanhthudukien: TextView
    private lateinit var txt_tongdoanhthuthucte: TextView
    private lateinit var txt_tongchithucte: TextView
    private lateinit var fragment1: TextView
    private lateinit var fragment2: TextView
    private lateinit var fragment3: TextView
    private lateinit var fragment4: TextView
    private lateinit var txttongsp: TextView
    private lateinit var txttonkho: TextView
    private lateinit var txt_tongchidukien: TextView
    private lateinit var txt_tongkhachhang: TextView
    private lateinit var fragment_container: FrameLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment__dashboard, container, false)
        txt_tongdoanhthudukien = view.findViewById(R.id.txt_tongdoanhthudukien)
        txt_tongchithucte = view.findViewById(R.id.txt_tongchithucte)
        txt_tongdoanhthuthucte = view.findViewById(R.id.txt_tongdoanhthuthucte)
        fragment1 = view.findViewById(R.id.fragment1)
        fragment2 = view.findViewById(R.id.fragment2)
        fragment3 = view.findViewById(R.id.fragment3)
        fragment4 = view.findViewById(R.id.fragment4)
        txttongsp = view.findViewById(R.id.txttongsp)
        txttonkho = view.findViewById(R.id.txttonkho)
        txt_tongkhachhang = view.findViewById(R.id.txt_tongkhachhang)
        txt_tongchidukien = view.findViewById(R.id.txt_tongchidukien)
        fragment_container = view.findViewById(R.id.fragment_container)
        dbHelper = DatabaseHelper(requireContext())

        val tongdoanhthu = dbHelper.tongdoanhthu()
        val tongchi = dbHelper.tongchi()
        val tongsp = dbHelper.tongsanpham()
        val tongton = dbHelper.tongtonkho()
        val tongkh = dbHelper.tongkhachhang()
        val tongchithucte = dbHelper.tongchithucte()
        val tongdtthucte = dbHelper.tongthuthucte()

        txttonkho.text = tongton.toString()
        txttongsp.text = tongsp.toString()
        txt_tongdoanhthudukien.text = tongdoanhthu.toString()
        txt_tongchidukien.text  = tongchi.toString()
        txt_tongchithucte.text = tongchithucte.toString()
        txt_tongkhachhang.text = tongkh.toString()
        txt_tongdoanhthuthucte.text = tongdtthucte.toString()

        fragment1.setOnClickListener(View.OnClickListener {
            loadFragment(Fragment_dashboard_ngay())
            setBoldText(fragment1)
        })

        fragment2.setOnClickListener(View.OnClickListener {
            loadFragment(Fragment_dashboard_thang())
            setBoldText(fragment2)
        })

        fragment3.setOnClickListener(View.OnClickListener {
            loadFragment(Fragment_dashboard_quy())
            setBoldText(fragment3)
        })

        fragment4.setOnClickListener(View.OnClickListener {
            loadFragment(Fragment_dashboard_nam())
            setBoldText(fragment4)
        })

        return view
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }
    private var lastSelectedTextView: TextView? = null
    private fun setBoldText(selectedTextView: TextView) {
//        fragment1.setTypeface(null, android.graphics.Typeface.NORMAL)
//        fragment2.setTypeface(null, android.graphics.Typeface.NORMAL)
//        fragment3.setTypeface(null, android.graphics.Typeface.NORMAL)
//        fragment4.setTypeface(null, android.graphics.Typeface.NORMAL)
        lastSelectedTextView?.apply {
            setTypeface(null, android.graphics.Typeface.NORMAL)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.grey)) // Reset to default text color
        }

        // Set new selected TextView to bold and red color
        selectedTextView.setTypeface(null, android.graphics.Typeface.BOLD)
        selectedTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red)) // Set to red color

        // Update lastSelectedTextView to current selected TextView
        lastSelectedTextView = selectedTextView
    }
}