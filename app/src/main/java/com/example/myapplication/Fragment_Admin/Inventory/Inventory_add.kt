package com.example.myapplication.Fragment_Admin.Inventory

import DatabaseHelper
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.Data.Model.Model_inventory
import com.example.myapplication.R

class Inventory_add:AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var productid: EditText
    private lateinit var edt_quantity: EditText
    private lateinit var button_addinventory: Button
    private lateinit var button_cancel: Button
    private lateinit var imgv_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inventory_add)
        productid = findViewById(R.id.productid)
        edt_quantity = findViewById(R.id.edt_quantity)
        button_addinventory = findViewById(R.id.button_addinventory)
        button_cancel = findViewById(R.id.button_cancel)
        imgv_back = findViewById(R.id.imgv_back)
        dbHelper = DatabaseHelper(this)

        button_addinventory.setOnClickListener {
            addinventory();
        }
        imgv_back.setOnClickListener {
            finish()
        }
    }


    private fun addinventory() {
        val productid = productid.text.toString()
        val edt_quantity = edt_quantity.text.toString().toIntOrNull()

        if(productid != null && edt_quantity != null){
            val inventory = Model_inventory("",productid,edt_quantity);
            val status = dbHelper.addInventory(inventory)
            if(status > -1){
                Toast.makeText(this, "Thêm tồn kho thành công", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Thêm tồn kho không thành công", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}