package com.example.myapplication.Fragment_Admin.Order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.Data.Model.Model_OrderDetail
import com.example.myapplication.R

class Adapter_orderdetail(
    private val ctdonhang: MutableList<Model_OrderDetail>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter_orderdetail.ViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mactdh: TextView = view.findViewById(R.id.mactdh)
        val txtSl: TextView = view.findViewById(R.id.txt_sl)
        val txt_sp: TextView = view.findViewById(R.id.txt_sp)
        val txtThanhTien: TextView = view.findViewById(R.id.txt_thanhtien)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_orderdetail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ct = ctdonhang[position]
        holder.mactdh.text = ct.orderid
        holder.txtSl.text = ct.quantity.toString()
        holder.txt_sp.text = ct.productid
        holder.txtThanhTien.text = ct.total.toString()
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return ctdonhang.size
    }

    fun removeItem(position: Int) {
        if (position in 0 until ctdonhang.size) { // Ensure the position is valid
            ctdonhang.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
