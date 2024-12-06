package com.example.myapplication.Fragment_Admin.Product

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Fragment_Admin.Product.Adapter_Product
import com.example.myapplication.R

class Fragment_Product : Fragment(), Adapter_Product.OnItemClickListener {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: Adapter_Product
    private lateinit var category_spinner: Spinner
    private lateinit var productList: ArrayList<Model_product>
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val REQUEST_EDIT_PRODUCT = 1
        private const val ADD = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__product, container, false)

        val btnAdd: Button = view.findViewById(R.id.button_add)
        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), Product_Additems::class.java)
            startActivityForResult(intent, ADD)
        }

        dbHelper = DatabaseHelper(requireContext())
        category_spinner = view.findViewById(R.id.category_spinner)
        val producttype = dbHelper.getallproducttypename()
        recyclerView = view.findViewById(R.id.recyclerview)
        val adapterproductname = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, producttype)
        adapterproductname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category_spinner.adapter = adapterproductname
        getallproduct()


        return view
    }

    private fun getallproduct(){

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productList = dbHelper.getAllproduct()
        adapter = Adapter_Product(productList, this)
        recyclerView.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        val product = productList[position]
        val intent = Intent(requireContext(), Product_edit::class.java).apply {
            putExtra(Product_edit.EXTRA_PRODUCT, product)
        }
        startActivityForResult(intent, REQUEST_EDIT_PRODUCT)
    }

    override fun onDeleteClick(position: Int) {
        val product = productList[position]
        if (id != null) {
            dbHelper.deleteProduct(product.productid)
        }
        productList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PRODUCT && resultCode == Product_edit.RESULT_UPDATED) {
            data?.let {
                val updatedProduct = it.getSerializableExtra(Product_edit.EXTRA_PRODUCT) as Model_product
                val position = productList.indexOfFirst { product -> product.masp == updatedProduct.masp }
                if (position != -1) {
                    productList[position] = updatedProduct
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD ->getallproduct()
        }
    }
}
