package com.example.myapplication.Fragment_User.Cart

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_cart.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_cart : Fragment() {

private lateinit var rv_cart: RecyclerView
private lateinit var txt_idkh: TextView
private lateinit var txtaddress: EditText
private lateinit var checkout: Button
private lateinit var dbHelper: DatabaseHelper
 private var accountId : String = ""
    private var khid: String = ""
    private var cartItems: List<Pair<Model_product, Int>>  = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.user_cart, container, false)
        dbHelper = DatabaseHelper(requireContext())
        arguments?.let {
            accountId = it.getString("account_id").toString()
        }
        khid = dbHelper.getIDKH(accountId)
        cartItems = dbHelper.getCartWithQuantity(khid)
//
        rv_cart = view.findViewById(R.id.rv_cart)
        checkout = view.findViewById(R.id.checkout)
        txtaddress = view.findViewById(R.id.txtaddress)
        rv_cart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val cart = dbHelper.getCartWithQuantity(khid)
        val adapter = Adapter_Cart(cart)
        rv_cart.adapter = adapter

        checkout.setOnClickListener {
            val orderId = dbHelper.checkout(cartItems,khid, txtaddress.text.toString(), "")
            if (orderId.isNotEmpty()) {
                Toast.makeText(requireContext(), "Order placed successfully! Order ID: $orderId", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Failed to place order. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
        return view

    }


}