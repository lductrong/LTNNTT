package com.example.myapplication.Fragment_User.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_producttype
import com.example.myapplication.R

class Adapter_category(private val categoryList: List<Model_producttype>): RecyclerView.Adapter<Adapter_category.CategoryViewHolder>() {
    class CategoryViewHolder(view: View):  RecyclerView.ViewHolder(view){
        val txt_category: TextView = view.findViewById(R.id.txt_category)
        val img_category: ImageView = view.findViewById(R.id.img_category)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_categories, parent, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val productstype = categoryList[position]
        holder.txt_category.text = productstype.tenlsp
        Glide.with(holder.itemView.context)
            .load(productstype.imglsp) // Đường dẫn tới file ảnh
            .into(holder.img_category)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}