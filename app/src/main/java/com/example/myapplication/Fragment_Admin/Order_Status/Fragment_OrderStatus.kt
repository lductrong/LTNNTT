package com.example.myapplication.Fragment_Admin.Order_Status

import DatabaseHelper
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_OrderStatus
import com.example.myapplication.R


class Fragment_OrderStatus : Fragment(),  Adapter_OrderStatus.OnItemClickListener {

    private lateinit var button_newstatus: Button
    private lateinit var rvorderstatus: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var orderstatus: ArrayList<Model_OrderStatus>
    private lateinit var adapter: Adapter_OrderStatus
    companion object {
        private const val REQUEST_EDIT_ORDER_STATUS = 2
        private const val ADD = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment__order_status, container, false)
        button_newstatus = view.findViewById(R.id.button_newstatus)
        rvorderstatus = view.findViewById(R.id.rvorderstatus)
        dbHelper = DatabaseHelper(requireContext())

        button_newstatus.setOnClickListener {
            val intent = Intent(requireContext(), OrderStatus_add::class.java)
            startActivityForResult(intent, ADD)
        }


        getallOrderStatus()
        return view
    }

    private fun getallOrderStatus(){
        rvorderstatus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        orderstatus = dbHelper.getAllOrderStatus()
        adapter = Adapter_OrderStatus(orderstatus, this)
        rvorderstatus.adapter = adapter

    }
    override fun onEditClick(position: Int) {
        val orderStatus = orderstatus[position]
        val intent = Intent(requireContext(), Orderstatus_edit::class.java).apply {
            putExtra(Orderstatus_edit.EXTRA_ORDER_STATUS, orderStatus)
        }
        startActivityForResult(intent, REQUEST_EDIT_ORDER_STATUS)
    }

    override fun onDeleteClick(position: Int) {
        val statusorder = orderstatus[position]

        dbHelper.deleteOrderstatus(statusorder.orderstatusid)
        orderstatus.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_ORDER_STATUS && resultCode == Orderstatus_edit.RESULT_UPDATED ||resultCode== Activity.RESULT_OK ) {
            data?.let {
                val updatedOrderStatus = it.getSerializableExtra(Orderstatus_edit.EXTRA_ORDER_STATUS) as Model_OrderStatus
                val position = orderstatus.indexOfFirst { status -> status.orderstatusid == updatedOrderStatus.orderstatusid }
                if (position != -1) {
                    orderstatus[position] = updatedOrderStatus
                    adapter.notifyItemChanged(position)
                }
            }
            when(requestCode){
                ADD ->getallOrderStatus()
            }
        }
    }



}