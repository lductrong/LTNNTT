package com.example.myapplication.Fragment_Admin.ProductType

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.Fragment_Admin.Product.Fragment_Product
import com.example.myapplication.Fragment_Admin.Product.Product_edit
import com.example.myapplication.R

class Fragment_producttype : Fragment(), Adapter_producttype.OnItemClickListener {
    private lateinit var rvproducttype: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private  lateinit var button_addproducttype: Button
    private lateinit var adapter: Adapter_producttype
    private lateinit var producttypeList: ArrayList<Model_producttype>
    companion object {
        private const val REQUEST_EDIT_PRODUCTTYPE = 1
        private const val ADD = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment__producttype, container, false)
        dbHelper = DatabaseHelper(requireContext())
        button_addproducttype = view.findViewById(R.id.button_addproducttype)
        rvproducttype = view.findViewById(R.id.rvproducttype)

        button_addproducttype.setOnClickListener {
            val intent = Intent(requireContext(), Producttype_Addtype::class.java)
            startActivityForResult(intent, ADD)
        }

getallproducttype()

        return view;
    }
    private fun getallproducttype(){
        rvproducttype.layoutManager = LinearLayoutManager(requireContext())
        producttypeList = dbHelper.getAllProductType()
        adapter = Adapter_producttype(producttypeList, this)
        rvproducttype.adapter = adapter

    }
    override fun onEditClick(position: Int) {
        val producttype = producttypeList[position]
        val intent = Intent(requireContext(), Producttype_edit::class.java).apply {
            putExtra(Producttype_edit.EXTRA_PRODUCTTYPE, producttype)
        }
        startActivityForResult(intent, REQUEST_EDIT_PRODUCTTYPE)
    }

    override fun onDeleteClick(position: Int) {
        val producttype = producttypeList[position]
        // Implement your delete functionality here
        if (id != null) {
            dbHelper.deleteProducttype(producttype.producttypeid)
        }
        producttypeList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PRODUCTTYPE && resultCode == Producttype_edit.RESULT_UPDATED) {
            data?.let {
                val updatedproducttype = it.getSerializableExtra(Producttype_edit.EXTRA_PRODUCTTYPE) as Model_producttype
                val position = producttypeList.indexOfFirst { producttype -> producttype.producttypeid == updatedproducttype.producttypeid }
                if (position != -1) {
                    producttypeList[position] = updatedproducttype
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD -> getallproducttype()
        }
    }
}