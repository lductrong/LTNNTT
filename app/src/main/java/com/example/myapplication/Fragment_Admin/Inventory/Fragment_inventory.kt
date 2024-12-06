package com.example.myapplication.Fragment_Admin.Inventory

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_inventory
import com.example.myapplication.R

class Fragment_inventory : Fragment(), Adapter_inventory.OnItemClickListener {
    private lateinit var button_addinventory: Button
    private lateinit var rvinventory: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: Adapter_inventory
    private lateinit var inventoryList: ArrayList<Model_inventory>
    companion object {
        private const val REQUEST_EDIT_INVENTORY = 1
        private const val ADD = 1
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_inventory, container, false)
        button_addinventory = view.findViewById(R.id.button_addinventory)
        rvinventory = view.findViewById(R.id.rvinventory)
        dbHelper = DatabaseHelper(requireContext())

        button_addinventory.setOnClickListener {
            val intent = Intent(requireContext(), Inventory_add::class.java)
            startActivityForResult(intent, ADD)
        }
        getallinventory()


        return view
    }
    private  fun getallinventory(){
        rvinventory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        inventoryList = dbHelper.getAllInventory()
        adapter = Adapter_inventory(inventoryList, this)
        rvinventory.adapter = adapter
    }
    override fun onEditClick(position: Int) {
        val inventory = inventoryList[position]
        val intent = Intent(requireContext(), Inventory_edit::class.java).apply {
            putExtra(Inventory_edit.EXTRA_INVENTORY, inventory)
        }
        startActivityForResult(intent, REQUEST_EDIT_INVENTORY)
    }

    override fun onDeleteClick(position: Int) {
        val inventory = inventoryList[position]
        // Implement your delete functionality here
        if (id != null) {
            dbHelper.deleteInventory(inventory.inventoryid)
        }
        inventoryList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_INVENTORY && resultCode == Inventory_edit.RESULT_UPDATED) {
            data?.let {
                val updatedInventory = it.getSerializableExtra(Inventory_edit.EXTRA_INVENTORY) as Model_inventory
                val position = inventoryList.indexOfFirst { inventory -> inventory.inventoryid == updatedInventory.inventoryid }
                if (position != -1) {
                    inventoryList[position] = updatedInventory
                    adapter.notifyItemChanged(position)
                }
            }
        }
        when(requestCode){
            ADD -> getallinventory()
        }
    }
}