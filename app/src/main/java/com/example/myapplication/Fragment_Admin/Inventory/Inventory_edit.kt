package com.example.myapplication.Fragment_Admin.Inventory

import DatabaseHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Model.Model_inventory
import com.example.myapplication.Data.Model.Model_staff
import com.example.myapplication.Fragment_Admin.Staff.Staff_edit
import com.example.myapplication.R

class Inventory_edit : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    companion object {
        const val EXTRA_INVENTORY = "inventory"
        const val RESULT_UPDATED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_inventory)

        val editTextProductId: EditText = findViewById(R.id.productid)
        val editTextQuantity: EditText = findViewById(R.id.edt_quantity)
        val buttonSave: Button = findViewById(R.id.button_addinventory)
        val buttonCancel: Button = findViewById(R.id.button_cancel)

        dbHelper = DatabaseHelper(this)
        val inventory = intent.getSerializableExtra(EXTRA_INVENTORY) as Model_inventory
        editTextProductId.setText(inventory.inventoryid)
        editTextQuantity.setText(inventory.quantity.toString())

        buttonSave.setOnClickListener {
            val updatedInventory = Model_inventory(
                inventoryid = inventory.inventoryid,
                productId = editTextProductId.text.toString(),
                quantity = editTextQuantity.text.toString().toInt()
            )

            if (updatedInventory.productId.isNotEmpty() && updatedInventory.quantity != null) {
                dbHelper.updateInventory(updatedInventory, updatedInventory.inventoryid)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_INVENTORY, updatedInventory)
                setResult(RESULT_UPDATED, resultIntent)
                finish()
            } else {
                showInvalidDataDialog()
            }
        }

        buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun showInvalidDataDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo")
        builder.setMessage("Dữ liệu không hợp lệ!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
