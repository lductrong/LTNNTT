package com.example.myapplication.Fragment_Admin.PhieuNhapStatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_Phieunhap_status
import com.example.myapplication.Fragment_Admin.PhieuNhap.Adapter_PhieuNhap
import com.example.myapplication.R

class Adapter_PhieuNhapStatus(private var pnstatuslist : List<Model_Phieunhap_status>, private var listener: OnItemClickListener) : RecyclerView.Adapter<Adapter_PhieuNhapStatus.PNStatusViewHolder>() {
    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    class PNStatusViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val txt_pnstatus = view.findViewById<TextView>(R.id.txt_pnstatus)
        val txt_pnid = view.findViewById<TextView>(R.id.txt_pnid)
        val btnedit: Button = view.findViewById(R.id.btnedit)
        val btndelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PNStatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_phieunhap_status, parent, false)

        return PNStatusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pnstatuslist.size
    }

    override fun onBindViewHolder(holder: Adapter_PhieuNhapStatus.PNStatusViewHolder, position: Int) {
        val pnstatus = pnstatuslist[position]
        holder.txt_pnid.text = "ID: "+ pnstatus.pnstatusid
        holder.txt_pnstatus.text = "Trạng thái: "+ pnstatus.status
        holder.btnedit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btndelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }
}