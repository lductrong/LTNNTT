package com.example.myapplication.Fragment_Admin.Order

import DatabaseHelper
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_Order
import com.example.myapplication.R
import java.util.Calendar

class Fragment_Order : Fragment(), Adapter_Order.OnItemClickListener, Adapter_Order.OnClickListener, Adapter_Order.OnInfoClickListener {

    private lateinit var rvOrder: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edtDay1: EditText
    private lateinit var btnDay1: ImageButton
    private lateinit var edtDay2: EditText
    private lateinit var btnDay2: ImageButton
    private lateinit var buttonNewOrder: Button
    private lateinit var adapter: Adapter_Order
    private var orderList: ArrayList<Model_Order> = arrayListOf()

    companion object {
        private const val REQUEST_EDIT_ORDER = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__order, container, false)

        rvOrder = view.findViewById(R.id.rvorder)
        dbHelper = DatabaseHelper(requireContext())
        edtDay1 = view.findViewById(R.id.edt_day1)
        btnDay1 = view.findViewById(R.id.btnday1)
        edtDay2 = view.findViewById(R.id.edt_day2)
        btnDay2 = view.findViewById(R.id.btnday2)
        buttonNewOrder = view.findViewById(R.id.button_neworder)

        rvOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        orderList = dbHelper.getAlOrder() // Ensure this method returns ArrayList<Model_Order>
        adapter = Adapter_Order(orderList, this, this, this)
        rvOrder.adapter = adapter

        btnDay1.setOnClickListener { openDay1() }
        btnDay2.setOnClickListener { openDay2() }
        buttonNewOrder.setOnClickListener {
            val intent = Intent(requireContext(), Order_add::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun openDay1() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val txt = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            edtDay1.setText(txt)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun openDay2() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val txt = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            edtDay2.setText(txt)
        }, year, month, day)

        datePickerDialog.show()
    }

    override fun onEditClick(position: Int) {
        val order = orderList[position]
        val intent = Intent(requireContext(), Order_edit::class.java).apply {
            putExtra(Order_edit.EXTRA_ORDER, order)
        }
        startActivityForResult(intent, REQUEST_EDIT_ORDER)
    }

    override fun onDeleteClick(position: Int) {
        // Implement your delete functionality here
    }

    override fun onClick(position: Int, model: Model_Order) {
        val intent = Intent(requireContext(), OrderDetail_add::class.java)
        intent.putExtra("orderid", model.order_id)
        startActivity(intent)
    }

    override fun onInfoClick(position: Int, model: Model_Order) {
        val intent = Intent(requireContext(), OrderDetails::class.java)
        intent.putExtra("orderid", model.order_id)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_ORDER && resultCode == Order_edit.RESULT_UPDATED) {
            data?.let {
                val updatedOrder = it.getSerializableExtra(Order_edit.EXTRA_ORDER) as Model_Order
                val position = orderList.indexOfFirst { order -> order.order_id == updatedOrder.order_id }
                if (position != -1) {
                    orderList[position] = updatedOrder
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }
}
