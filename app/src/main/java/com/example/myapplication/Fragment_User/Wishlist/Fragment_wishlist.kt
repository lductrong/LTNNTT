package com.example.myapplication.Fragment_User.Wishlist

import DatabaseHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Fragment_User.Cart.Adapter_Cart
import com.example.myapplication.R

class Fragment_wishlist : Fragment() {
    private lateinit var rv_wishlist: RecyclerView
    private lateinit var txtid: TextView
    private lateinit var dbHelper: DatabaseHelper
    private var accountId : String = ""
    private var khid: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view =  inflater.inflate(R.layout.user_wishlist, container, false)
        dbHelper = DatabaseHelper(requireContext())
        arguments?.let {
            accountId = it.getString("account_id").toString()
        }


        khid = dbHelper.getIDKH(accountId)
        rv_wishlist = view.findViewById(R.id.rv_wishlist)
        rv_wishlist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val wishlist = dbHelper.getWishList(khid)
        val adapter = Adapter_wishlist(wishlist)
        rv_wishlist.adapter = adapter

        return view
    }

}