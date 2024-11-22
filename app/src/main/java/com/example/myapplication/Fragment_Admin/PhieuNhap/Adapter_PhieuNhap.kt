package com.example.myapplication.Fragment_Admin.PhieuNhap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_Phieunhap
import com.example.myapplication.R

class Adapter_PhieuNhap(
    private val phieunhap: List<Model_Phieunhap>,
    private val onClickListener: OnClickListener,
    private val onInfoClickListener: OnInfoClickListener,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter_PhieuNhap.PhieuNhapViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    class PhieuNhapViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name_phieunhap: TextView = view.findViewById(R.id.name_phieunhap)
        val txt_day: TextView = view.findViewById(R.id.txt_day)
        val txt_nsx: TextView = view.findViewById(R.id.txt_nsx)
        val txt_thanhtien: TextView = view.findViewById(R.id.txt_thanhtien)
        val btnedit: Button = view.findViewById(R.id.btnedit)
        val btndelete: Button = view.findViewById(R.id.btndelete)
        val mapn: TextView = view.findViewById(R.id.mapn)
        val infor: ImageView = view.findViewById(R.id.infor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhieuNhapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_phieunhap, parent, false)
        return PhieuNhapViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhieuNhapViewHolder, position: Int) {
        val values = phieunhap[position]

        holder.name_phieunhap.text = values.ghichu
        holder.mapn.text = "Mã phiếu nhập: ${values.idpn}"
        holder.txt_nsx.text = "Nhà sản xuất: ${values.nsx}"
        holder.txt_day.text = "Ngày nhập: ${values.ngayNhap}"
        holder.txt_thanhtien.text = "Thành tiền: ${values.thanhTien}"

        holder.itemView.setOnClickListener {
            onClickListener.onClick(position, values)
        }
        holder.infor.setOnClickListener {
            onInfoClickListener.onClickinfor(position, values)
        }

        holder.btnedit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btndelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return phieunhap.size
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Model_Phieunhap)
    }
    interface OnInfoClickListener {
        fun onClickinfor(position: Int, model: Model_Phieunhap)
    }
}


