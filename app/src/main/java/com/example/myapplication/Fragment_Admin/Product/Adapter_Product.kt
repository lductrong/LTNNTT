package com.example.myapplication.Fragment_Admin.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Adapter_Product(
    private val productList: ArrayList<Model_product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter_Product.ProductHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    class ProductHolder(viewitem: View) : RecyclerView.ViewHolder(viewitem) {
        val txt_tensp: TextView = viewitem.findViewById(R.id.txt_tensp)
        val txt_gia: TextView = viewitem.findViewById(R.id.txt_gia)
        val txt_theloai: TextView = viewitem.findViewById(R.id.txt_theloai)
        val txt_donvi: TextView = viewitem.findViewById(R.id.txt_donvi)
        val img_product: ImageView = viewitem.findViewById(R.id.img_product)
        val btnEdit: Button = viewitem.findViewById(R.id.btnedit)
        val btnDelete: Button = viewitem.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_products, parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = productList[position]
        holder.txt_tensp.text = "Sản phẩm: " + product.tensp
        holder.txt_gia.text = "Giá: " + product.gia.toString()
        holder.txt_theloai.text = "Mã loại: " + product.maloai
        holder.txt_donvi.text = "Đơn vị: " + product.donvi
        Glide.with(holder.itemView).load(product.img).into(holder.img_product)

        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

