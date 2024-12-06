package com.example.myapplication.Fragment_User.Wishlist

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

class Adapter_wishlist(private val wishlistList: ArrayList<Model_product>) :
    RecyclerView.Adapter<Adapter_wishlist.CartViewHolder>() {
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_nameproduct: TextView = view.findViewById(R.id.txt_nameproduct)
        val txt_price: TextView = view.findViewById(R.id.txt_price)
        val imageproduct: ImageView = view.findViewById(R.id.imageproduct)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_wishlist.CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_wishlist, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_wishlist.CartViewHolder, position: Int) {
        val test = 0 //tester
        val cart = wishlistList[position]
        holder.txt_nameproduct.text = cart.tensp
        holder.txt_price.text = cart.gia.toString()
        Glide.with(holder.itemView.context).load(cart.img).into(holder.imageproduct)
    }

    override fun getItemCount(): Int {
        return wishlistList.size
    }

}