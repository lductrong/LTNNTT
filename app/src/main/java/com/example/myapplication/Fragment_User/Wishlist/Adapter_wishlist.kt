package com.example.myapplication.Fragment_User.Wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_cart
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Adapter_wishlist(
    private val wishlistList: ArrayList<Model_product>,
    private val onDeleteClick: (Model_product) -> Unit,
    private val onAddToCartClick: (Model_product, Int) -> Unit // Thêm tham số cho số lượng
) : RecyclerView.Adapter<Adapter_wishlist.WishlistViewHolder>() {

    class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNameProduct: TextView = view.findViewById(R.id.txt_nameproduct)
        val txtPrice: TextView = view.findViewById(R.id.txt_price)
        val imageProduct: ImageView = view.findViewById(R.id.imageproduct)
        val btnAddToCart: Button = view.findViewById(R.id.btn_addcart)
        val btnDelete: Button = view.findViewById(R.id.btn_del)
        val btnIncrease: Button = view.findViewById(R.id.btn_increase)
        val btnDecrease: Button = view.findViewById(R.id.btn_decrease)
        val text_quantity: TextView = view.findViewById(R.id.text_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val product = wishlistList[position]
        holder.txtNameProduct.text = product.tensp
        holder.txtPrice.text = "${product.gia} đ"
        Glide.with(holder.itemView.context).load(product.img).into(holder.imageProduct)

        var quantity = 1 // Thiết lập giá trị khởi đầu cho số lượng
        holder.text_quantity.text = quantity.toString()

        holder.btnIncrease.setOnClickListener {
            quantity += 1
            holder.text_quantity.text = quantity.toString()
        }

        holder.btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity -= 1
                holder.text_quantity.text = quantity.toString()
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Số lượng không thể nhỏ hơn 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(product, quantity) // Truyền cả sản phẩm và số lượng
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(product)
        }
    }

    override fun getItemCount(): Int = wishlistList.size
}