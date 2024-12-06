package com.example.myapplication.Fragment_Admin.Order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_Order
import com.example.myapplication.R

class Adapter_Order(
    private var orderList: List<Model_Order>,
    private val onClickListener: OnClickListener,
    private val onInfoClickListener: OnInfoClickListener,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter_Order.OrderViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Model_Order)
    }

    interface OnInfoClickListener {
        fun onInfoClick(position: Int, model: Model_Order)
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtOrderDate: TextView = view.findViewById(R.id.txt_orderdate)
        val txtTotal: TextView = view.findViewById(R.id.txt_total)
        val txtIdCus: TextView = view.findViewById(R.id.txt_idcus)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
        val info: ImageView = view.findViewById(R.id.infor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.txtOrderDate.text = "Ngày đặt hàng: " + order.orderday
        holder.txtTotal.text = "Tổng tiền: " + order.thanhtien.toString()
        holder.txtIdCus.text = "Mã đơn hàng: " + order.customerid.toString()

        holder.itemView.setOnClickListener {
            onClickListener.onClick(position, order)
        }

        holder.info.setOnClickListener {
            onInfoClickListener.onInfoClick(position, order)
        }

        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }

        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}
