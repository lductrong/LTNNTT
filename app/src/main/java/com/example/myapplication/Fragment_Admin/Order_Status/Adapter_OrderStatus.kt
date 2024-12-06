package com.example.myapplication.Fragment_Admin.Order_Status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_OrderStatus
import com.example.myapplication.R

class Adapter_OrderStatus(private var orderstatuslist: List<Model_OrderStatus>, private var listener: OnItemClickListener) : RecyclerView.Adapter<Adapter_OrderStatus.OrderStatusHolder>() {
    class OrderStatusHolder(view: View) :RecyclerView.ViewHolder(view) {
        val txt_orderid = view.findViewById<TextView>(R.id.txt_orderid)
        val txt_orderstatus = view.findViewById<TextView>(R.id.txt_orderstatus)
        val btnedit = view.findViewById<TextView>(R.id.btnedit)
        val btndelete = view.findViewById<TextView>(R.id.btndelete)
    }

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_OrderStatus.OrderStatusHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_orderstatus, parent, false)

        return OrderStatusHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_OrderStatus.OrderStatusHolder, position: Int) {
        val orderstatus = orderstatuslist[position]
        holder.txt_orderstatus.text = "Trạng thái: "+ orderstatus.status
        holder.btnedit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btndelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return  orderstatuslist.size
    }
}