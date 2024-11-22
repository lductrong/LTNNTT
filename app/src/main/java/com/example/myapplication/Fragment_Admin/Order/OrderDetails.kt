package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.Data.Model.Model_OrderDetail
import com.example.myapplication.Fragment_Admin.PhieuNhap.CT_PhieuNhap
import com.example.myapplication.Fragment_Admin.PhieuNhap.CT_PhieuNhap_edit
import com.example.myapplication.R

class OrderDetails : AppCompatActivity(), Adapter_orderdetail.OnItemClickListener {
    private lateinit var rvOrderDetails: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var orderDetailsList: ArrayList<Model_OrderDetail>
    private lateinit var adapter: Adapter_orderdetail
    private lateinit var imgvBack: ImageView
    private var orderId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orderdetail)

        dbHelper = DatabaseHelper(this)
        orderId = intent.getStringExtra("orderid") ?: ""

        if (orderId.isEmpty()) {
            finish()
            return
        }
        rvOrderDetails = findViewById(R.id.rvctphieunhap)
        imgvBack = findViewById(R.id.imgv_back)

        rvOrderDetails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        orderDetailsList = dbHelper.getAllCtDonhang(orderId) // Fetch order details using the orderId
        adapter = Adapter_orderdetail(orderDetailsList, this)
        rvOrderDetails.adapter = adapter
        imgvBack.setOnClickListener {
            finish()
        }
    }

    override fun onEditClick(position: Int) {
        val orderDetail = orderDetailsList[position]
        val intent = Intent(this, Orderdetail_edit::class.java).apply {
            putExtra(Orderdetail_edit.EXTRA_ORDERDETAIL, orderDetail)
        }
        startActivityForResult(intent, REQUEST_EDIT_ORDER_DETAIL)
    }
    companion object {
        private const val REQUEST_EDIT_ORDER_DETAIL = 5
    }
    override fun onDeleteClick(position: Int) {
//        val item = orderDetailsList[position]
//        val result = dbHelper.deletectphieunhap(item.orderdetailid)
//        if (result > 0) {
//            adapter.removeItem(position)
//        } else {
//            // Handle the error case
//        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OrderDetails.REQUEST_EDIT_ORDER_DETAIL && resultCode == Orderdetail_edit.RESULT_UPDATED) {
            data?.let {
                val updatedOrderDetails = it.getSerializableExtra(Orderdetail_edit.EXTRA_ORDERDETAIL) as Model_OrderDetail
                val position = orderDetailsList.indexOfFirst { item -> item.orderid == updatedOrderDetails.orderid }
                if (position != -1) {
                    orderDetailsList[position] = updatedOrderDetails
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }
}
