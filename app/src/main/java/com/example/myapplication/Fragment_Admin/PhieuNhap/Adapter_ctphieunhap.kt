package com.example.myapplication.Fragment_Admin.PhieuNhap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_CTPhieuNhap
import com.example.myapplication.R

class Adapter_ctphieunhap(
    private val ctphieunhap: MutableList<Model_CTPhieuNhap>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter_ctphieunhap.ViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mactpn: TextView = view.findViewById(R.id.mactpn)
        val txtSl: TextView = view.findViewById(R.id.txt_sl)
        val txtDonGia: TextView = view.findViewById(R.id.txt_dongia)
        val txtThanhTien: TextView = view.findViewById(R.id.txt_thanhtien)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ctphieunhap, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ct = ctphieunhap[position]
        holder.mactpn.text = "Mã CTPN: " + ct.idctpn
        holder.txtSl.text = "Số lượng: " + ct.soLuong.toString()
        holder.txtDonGia.text = "Đơn giá: " +  ct.donGia.toString()
        holder.txtThanhTien.text = "Thành tiền: " + ct.thanhTien.toString()
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return ctphieunhap.size
    }

    fun removeItem(position: Int) {
        if (position in 0 until ctphieunhap.size) { // Ensure the position is valid
            ctphieunhap.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
