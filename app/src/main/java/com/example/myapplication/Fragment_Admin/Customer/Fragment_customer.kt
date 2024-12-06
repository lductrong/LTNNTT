package com.example.myapplication.Fragment_Admin.Customer

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Fragment_Admin.Product.Adapter_Product
import com.example.myapplication.R

class Fragment_customer : Fragment(),Adapter_customer.OnItemClickListener {
    private lateinit var btnnewcus: Button
    private lateinit var rvcustomer: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: Adapter_customer
    private lateinit var customerList: ArrayList<Model_customer>
    companion object {
        private const val REQUEST_EDIT_CUSTOMER = 1
        private const val ADD = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_customer, container, false)
        dbHelper = DatabaseHelper(requireContext())
        btnnewcus = view.findViewById(R.id.button_newcus)
        rvcustomer = view.findViewById(R.id.rvcustomer)

        btnnewcus.setOnClickListener {
            val intent = Intent(requireContext(), Customer_add::class.java)
            startActivityForResult(intent, ADD)

        }

        getallcustomer()

        return view;
    }
    private fun getallcustomer(){
        rvcustomer.layoutManager = LinearLayoutManager(requireContext())
        customerList = dbHelper.getAllCustomer()
        adapter = Adapter_customer(customerList, this)
        rvcustomer.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        val customer = customerList[position]
        val intent = Intent(requireContext(), Customer_edit::class.java).apply {
            putExtra(Customer_edit.EXTRA_CUSTOMER, customer)
        }
        startActivityForResult(intent, REQUEST_EDIT_CUSTOMER)
    }

    override fun onDeleteClick(position: Int) {
        val customer = customerList[position]
        // Implement your delete functionality here
        if (id != null) {
            dbHelper.deleteCustomer(customer.customerid)
        }
        customerList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_CUSTOMER && resultCode == Customer_edit.RESULT_UPDATED) {
            data?.let {
                val updatedCustomer = it.getSerializableExtra(Customer_edit.EXTRA_CUSTOMER) as Model_customer
                val position = customerList.indexOfFirst { customer -> customer.customerid == updatedCustomer.customerid }
                if (position != -1) {
                    customerList[position] = updatedCustomer
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD -> getallcustomer()
        }
    }

}