package com.example.myapplication.Fragment_User.Cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_cart
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Adapter_Cart(private val cartList: List<Pair<Model_product, Int>>) : RecyclerView.Adapter<Adapter_Cart.CartViewHolder>() {
    class CartViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txt_nameproduct: TextView = view.findViewById(R.id.txt_nameproduct)
        val txt_price: TextView = view.findViewById(R.id.txt_price)
        val txt_quantity: TextView = view.findViewById(R.id.txt_quantity)
        val imageproduct: ImageView = view.findViewById(R.id.imageproduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Cart.CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cart, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_Cart.CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.txt_nameproduct.text = cart.first.tensp
        holder.txt_price.text = cart.first.gia.toString()
        holder.txt_quantity.text =cart.second.toString()
        Glide.with(holder.itemView.context).load(cart.first.img).into(holder.imageproduct)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

}