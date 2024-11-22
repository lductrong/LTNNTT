package com.example.myapplication.Fragment_Admin.Customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Mode.Model_customer
import com.example.myapplication.Fragment_Admin.Product.Adapter_Product
import com.example.myapplication.R

class Adapter_customer(private val customerList: List<Model_customer>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<Adapter_customer.CustomerHolder>() {
    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    class CustomerHolder(view : View): RecyclerView.ViewHolder(view){
        val txt_namecus: TextView = view.findViewById(R.id.txt_namecus)
        val txt_addresscus: TextView = view.findViewById(R.id.txt_addresscus)
        val txt_phone: TextView = view.findViewById(R.id.txt_phone)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_customer.CustomerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_customer, parent, false)

        return CustomerHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_customer.CustomerHolder, position: Int) {
        val customer = customerList[position]
        holder.txt_namecus.text = customer.namecus
        holder.txt_addresscus.text = customer.addresscus
        holder.txt_phone.text = customer.daycus
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}