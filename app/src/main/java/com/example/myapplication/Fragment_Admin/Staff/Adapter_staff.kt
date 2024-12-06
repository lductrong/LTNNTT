package com.example.myapplication.Fragment_Admin.Staff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.Fragment_Admin.Product.Adapter_Product
import com.example.myapplication.R

class Adapter_staff(private val staffList : List<Model_staff>,private val listener: OnItemClickListener): RecyclerView.Adapter<Adapter_staff.StaffViewHolder>() {
    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_namestaff: TextView = view.findViewById(R.id.txt_namestaff)
        val txt_addressstaff: TextView = view.findViewById(R.id.txt_addressstaff)
        val txt_workday: TextView = view.findViewById(R.id.txt_workday)
        val btnEdit: Button = view.findViewById(R.id.btnedit)
        val btnDelete: Button = view.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_staff.StaffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_staff, parent, false)

        return StaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter_staff.StaffViewHolder, position: Int) {
        val staff = staffList[position]
        holder.txt_namestaff.text = staff.tennv
        holder.txt_addressstaff.text = staff.dcnv
        holder.txt_workday.text = staff.ngaylam
        holder.btnEdit.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return staffList.size
    }
}