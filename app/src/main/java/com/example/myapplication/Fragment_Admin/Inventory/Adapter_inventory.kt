package com.example.myapplication.Fragment_Admin.Inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_inventory
import com.example.myapplication.Fragment_Admin.Customer.Adapter_customer
import com.example.myapplication.R

class Adapter_inventory(private val inventorylist: List<Model_inventory>, private val listener: OnItemClickListener): RecyclerView.Adapter<Adapter_inventory.InventoryViewHolder>() {
    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    class InventoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txt_masp: TextView = view.findViewById(R.id.txt_masp)
        val txt_slton: TextView = view.findViewById(R.id.txt_slton)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_inventory.InventoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_inventory.InventoryViewHolder, position: Int) {
        val inventory = inventorylist[position]
        holder.txt_masp.text = "Mã sản phẩm: "+ inventory.productId.toString()
        holder.txt_slton.text = "Số lượng tồn: "+ inventory.quantity.toString()
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return inventorylist.size
    }
}