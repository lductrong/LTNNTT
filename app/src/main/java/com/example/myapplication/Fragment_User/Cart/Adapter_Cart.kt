package com.example.myapplication.Fragment_User.Cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Adapter_Cart(
    private val cartList: MutableList<Pair<Model_product, Int>>,
    private val onQuantityChange: () -> Unit,
    private val onDeleteClick: (Model_product) -> Unit
) : RecyclerView.Adapter<Adapter_Cart.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_nameproduct: TextView = view.findViewById(R.id.txt_nameproduct)
        val txt_price: TextView = view.findViewById(R.id.txt_price)
        val txt_quantity: TextView = view.findViewById(R.id.txt_quantity)
        val imageproduct: ImageView = view.findViewById(R.id.imageproduct)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
        val btnIncrease: Button = view.findViewById(R.id.button_increase)
        val btnDecrease: Button = view.findViewById(R.id.button_decrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.txt_nameproduct.text = cart.first.tensp
        holder.txt_price.text = cart.first.gia.toString()
        holder.txt_quantity.text = cart.second.toString()

        Glide.with(holder.itemView.context).load(cart.first.img).into(holder.imageproduct)

        holder.btnIncrease.setOnClickListener {
            cartList[position] = cart.first to (cart.second + 1)
            notifyItemChanged(position)
            onQuantityChange()
        }

        holder.btnDecrease.setOnClickListener {
            if (cart.second > 1) {
                cartList[position] = cart.first to (cart.second - 1)
                notifyItemChanged(position)
                onQuantityChange()
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Số lượng không thể nhỏ hơn 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.btnDelete.setOnClickListener {
            cartList.removeAt(position)
            notifyItemRemoved(position)
            onDeleteClick(cart.first)
        }
    }

    override fun getItemCount(): Int = cartList.size
}