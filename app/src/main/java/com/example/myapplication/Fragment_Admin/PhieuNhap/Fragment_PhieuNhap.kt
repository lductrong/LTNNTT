package com.example.myapplication.Fragment_Admin.PhieuNhap

import DatabaseHelper
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.Data.Model.Model_Phieunhap
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Fragment_Admin.Order_Status.Fragment_OrderStatus
import com.example.myapplication.Fragment_Admin.Product.Adapter_Product
import com.example.myapplication.R
import java.util.Calendar

class Fragment_PhieuNhap : Fragment(), Adapter_PhieuNhap.OnClickListener, Adapter_PhieuNhap.OnItemClickListener, Adapter_PhieuNhap.OnInfoClickListener {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var btn_addphieunhap: Button
    private lateinit var rvphieunhap: RecyclerView
    private lateinit var adapter: Adapter_PhieuNhap
    private lateinit var edt_day1: EditText
    private lateinit var btnday1: ImageButton
    private lateinit var edt_day2: EditText
    private lateinit var btnday2: ImageButton

    private lateinit var phieunhapList: ArrayList<Model_Phieunhap>
    companion object {
        private const val REQUEST_EDIT_PHIEUNHAP = 3
        private const val ADD = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__phieunhap, container, false)
        btn_addphieunhap = view.findViewById(R.id.btn_addphieunhap)
        rvphieunhap = view.findViewById(R.id.rvphieunhap)
        dbHelper = DatabaseHelper(requireContext())
        edt_day1 = view.findViewById(R.id.edt_day1)
        btnday1 = view.findViewById(R.id.btnday1)
        edt_day2 = view.findViewById(R.id.edt_day2)
        btnday2 = view.findViewById(R.id.btnday2)

        btn_addphieunhap.setOnClickListener {
            val intent = Intent(requireContext(),Fragment_phieunhap_add::class.java)
            startActivityForResult(intent, ADD)
        }
        btnday1.setOnClickListener {
            openday1();
        }
        btnday2.setOnClickListener {
            openday2();
        }

        getallphieunhap()

        return view
    }

    private fun getallphieunhap(){
        rvphieunhap.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        phieunhapList = dbHelper.getAllPhieuNhap()
        adapter = Adapter_PhieuNhap(phieunhapList, this, this, this)
        rvphieunhap.adapter = adapter
    }

    override fun onClick(position: Int, model: Model_Phieunhap) {
        val intent = Intent(requireContext(), Fragment_ctphieunhap_add::class.java)
        intent.putExtra("mapn", model.idpn)
        startActivity(intent)
    }
    private fun openday1() {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val calander = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val txt = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            edt_day1.setText(txt);
        }, year, month, day)
        calander.show()
    }
    private fun openday2() {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val calander = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val txt = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            edt_day2.setText(txt);
        }, year, month, day)
        calander.show()
    }

    override fun onEditClick(position: Int) {
        val phieunhap = phieunhapList[position]
        val intent = Intent(requireContext(), Phieunhap_edit::class.java).apply {
            putExtra(Phieunhap_edit.EXTRA_PHIEUNHAP, phieunhap)
        }
        startActivityForResult(intent, REQUEST_EDIT_PHIEUNHAP)
    }

    override fun onDeleteClick(position: Int) {
        val phieunhap =  phieunhapList[position]
        // Implement your delete functionality here
        if (id != null) {
            dbHelper.deletePhieuNhap(phieunhap.idpn)
        }
        phieunhapList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onClickinfor(position: Int, model: Model_Phieunhap) {
        val intent = Intent(requireContext(), CT_PhieuNhap::class.java)
        intent.putExtra("mapn", model.idpn)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PHIEUNHAP && resultCode == Phieunhap_edit.RESULT_UPDATED ||resultCode== Activity.RESULT_OK ) {
            data?.let {
                val updatedPhieunhap = it.getSerializableExtra(Phieunhap_edit.EXTRA_PHIEUNHAP) as Model_Phieunhap
                val position = phieunhapList.indexOfFirst { phieu -> phieu.idpn == updatedPhieunhap.idpn }
                if (position != -1) {
                    phieunhapList[position] = updatedPhieunhap
                    adapter.notifyItemChanged(position)
                }
            }
            when(requestCode){
                ADD ->getallphieunhap()
            }
        }
    }

}
