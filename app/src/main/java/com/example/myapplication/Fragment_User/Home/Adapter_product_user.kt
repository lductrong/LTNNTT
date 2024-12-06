package com.example.myapplication.Fragment_User.Home

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Adapter_product_user(private val productuserList: List<Model_product>,
                           private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<Adapter_product_user.ProductUserViewHolder>() {
    class ProductUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_product: ImageView = view.findViewById(R.id.img_product)
        val txt_tensp: TextView = view.findViewById(R.id.txt_tensp)
        val txt_gia: TextView = view.findViewById(R.id.txt_gia)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_product_user.ProductUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product_user, parent, false)

        return ProductUserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductUserViewHolder,
        position: Int
    ) {
        val product = productuserList[position]
        holder.txt_gia.text = product.gia.toString()+ " đ"
        holder.txt_tensp.text = product.tensp
        Glide.with(holder.itemView.context)
            .load(product.img)
            .into(holder.img_product)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(position, product )
        }
    }

    override fun getItemCount(): Int {
        return  productuserList.size
    }


    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Model_product)
    }

}