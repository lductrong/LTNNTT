package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.R

class CT_PhieuNhap : AppCompatActivity(), Adapter_ctphieunhap.OnItemClickListener {
    private lateinit var rvctphieunhap: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var ctphieunhapList: ArrayList<Model_CTPhieuNhap>
    private lateinit var adapter: Adapter_ctphieunhap
    private lateinit var imgv_back: ImageView
    companion object {
        private const val REQUEST_EDIT_CT_PHIEUNHAP = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ctphieunhap)

        dbHelper = DatabaseHelper(this)
        val mapn = intent.getStringExtra("mapn")

        if (mapn.isNullOrEmpty()) {
            finish()
            return
        }

        rvctphieunhap = findViewById(R.id.rvctphieunhap)
        imgv_back = findViewById(R.id.imgv_back)

        rvctphieunhap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ctphieunhapList = dbHelper.getAllCtPhieuNhap(mapn) // Initialize the list here
        adapter = Adapter_ctphieunhap(ctphieunhapList, this)
        rvctphieunhap.adapter = adapter
        imgv_back.setOnClickListener {
            finish()
        }
    }

    override fun onEditClick(position: Int) {
        val item = ctphieunhapList[position]
        val intent = Intent(this, CT_PhieuNhap_edit::class.java).apply {
            putExtra(CT_PhieuNhap_edit.EXTRA_CT_PHIEUNHAP, item)
        }
        startActivityForResult(intent, REQUEST_EDIT_CT_PHIEUNHAP)
    }

    override fun onDeleteClick(position: Int) {
        val item = ctphieunhapList[position]
        val result = dbHelper.deletectphieunhap(item.idctpn)
        if (result > 0) {
            adapter.removeItem(position)
        } else {
            // Handle the error case
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_CT_PHIEUNHAP && resultCode == CT_PhieuNhap_edit.RESULT_UPDATED) {
            data?.let {
                val updatedCTPhieuNhap = it.getSerializableExtra(CT_PhieuNhap_edit.EXTRA_CT_PHIEUNHAP) as Model_CTPhieuNhap
                val position = ctphieunhapList.indexOfFirst { item -> item.idctpn == updatedCTPhieuNhap.idctpn }
                if (position != -1) {
                    ctphieunhapList[position] = updatedCTPhieuNhap
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }

}
