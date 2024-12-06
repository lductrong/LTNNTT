package com.example.myapplication.Fragment_Admin.ProductType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.Fragment_Admin.Customer.Adapter_customer
import com.example.myapplication.R

class Adapter_producttype(private val producttypeList : ArrayList<Model_producttype>,private val listener: OnItemClickListener): RecyclerView.Adapter<Adapter_producttype.ProductTypeViewHolder>() {
    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_producttype.ProductTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_prodcuttype, parent, false)

       return ProductTypeViewHolder(view);
    }

    class ProductTypeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txt_tenlsp: TextView = view.findViewById(R.id.txt_tenlsp)
        val imgv_imgsource: ImageView = view.findViewById(R.id.imgv_imgsource)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onBindViewHolder(
        holder: Adapter_producttype.ProductTypeViewHolder,
        position: Int
    ) {
        val productstype = producttypeList[position]
        holder.txt_tenlsp.text = productstype.tenlsp
        Glide.with(holder.itemView.context)
            .load(productstype.imglsp)
            .into(holder.imgv_imgsource)
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
       return producttypeList.size
    }
}