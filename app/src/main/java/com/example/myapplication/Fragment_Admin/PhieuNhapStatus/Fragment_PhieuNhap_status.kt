package com.example.myapplication.Fragment_Admin.PhieuNhapStatus

import DatabaseHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_Phieunhap_status
import com.example.myapplication.R

class Fragment_PhieuNhap_status : Fragment(), Adapter_PhieuNhapStatus.OnItemClickListener {
    private lateinit var button_newstatus:Button
    private lateinit var rvpnstatus:RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: Adapter_PhieuNhapStatus
    private lateinit var pnstatuslist: ArrayList<Model_Phieunhap_status>
    companion object {
        private const val REQUEST_EDIT_PN_STATUS = 2
        private const val ADD = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment__phieu_nhap_status, container, false)
        button_newstatus = view.findViewById(R.id.button_newstatus)
        rvpnstatus = view.findViewById(R.id.rvpnstatus)
        dbHelper = DatabaseHelper(requireContext())

        button_newstatus.setOnClickListener {
            val intent = Intent(requireContext(), PhieuNhapStatus_add::class.java)
            startActivityForResult(intent, ADD)
        }

        getallphieunhapstatus()
        return view
    }

    private fun getallphieunhapstatus(){
        rvpnstatus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pnstatuslist = dbHelper.getAllPNStatus()
        adapter = Adapter_PhieuNhapStatus(pnstatuslist, this)
        rvpnstatus.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        val pnStatus = pnstatuslist[position]
        val intent = Intent(requireContext(), Phieunhapstatus_edit::class.java).apply {
            putExtra(Phieunhapstatus_edit.EXTRA_PN_STATUS, pnStatus)
        }
        startActivityForResult(intent, REQUEST_EDIT_PN_STATUS)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onDeleteClick(position: Int) {
        val pnstatus =  pnstatuslist[position]
            dbHelper.deletePNStatus(pnstatus.pnstatusid)
        pnstatuslist.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PN_STATUS && resultCode == Phieunhapstatus_edit.RESULT_UPDATED || resultCode == ADD) {
            data?.let {
                val updatedPnStatus = it.getSerializableExtra(Phieunhapstatus_edit.EXTRA_PN_STATUS) as Model_Phieunhap_status
                val position = pnstatuslist.indexOfFirst { status -> status.pnstatusid == updatedPnStatus.pnstatusid }
                if (position != -1) {
                    pnstatuslist[position] = updatedPnStatus
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD -> getallphieunhapstatus()
        }
    }


}