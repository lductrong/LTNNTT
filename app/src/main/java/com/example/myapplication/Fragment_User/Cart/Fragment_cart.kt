package com.example.myapplication.Fragment_User.Cart

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R

class Fragment_cart : Fragment() {

    private lateinit var rv_cart: RecyclerView
    private lateinit var txtaddress: EditText
    private lateinit var checkout: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var txtTotalAmount: TextView
    private lateinit var txtShippingFee: TextView
    private lateinit var txtGrandTotal: TextView

    private var accountId: String = ""
    private var khid: String = ""
    private var cartItems: MutableList<Pair<Model_product, Int>> = mutableListOf()
    private lateinit var adapter: Adapter_Cart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_cart, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Lấy accountId từ arguments
        arguments?.let {
            accountId = it.getString("account_id").toString()
        }

        // Lấy khid từ database helper
        khid = dbHelper.getIDKH(accountId)
        cartItems = dbHelper.getCartWithQuantity(khid).toMutableList()

        // Gán các view
        rv_cart = view.findViewById(R.id.rv_cart)
        checkout = view.findViewById(R.id.checkout)
        txtaddress = view.findViewById(R.id.txtaddress)
        txtTotalAmount = view.findViewById(R.id.textView10)
        txtShippingFee = view.findViewById(R.id.textView15)
        txtGrandTotal = view.findViewById(R.id.textView135)

        // Khởi tạo adapter với các sự kiện callback
        adapter = Adapter_Cart(
            cartItems,
            onQuantityChange = {
                calculateAndDisplayTotals()
            },
            onDeleteClick = { product ->
                // Xóa sản phẩm khỏi giỏ hàng
                cartItems.removeAll { it.first == product }
                adapter.notifyDataSetChanged()
                calculateAndDisplayTotals()
            }
        )

        // Cấu hình RecyclerView
        rv_cart.layoutManager = LinearLayoutManager(requireContext())
        rv_cart.adapter = adapter

        // Tính toán và hiển thị tổng tiền
        calculateAndDisplayTotals()

        // Sự kiện checkout
        checkout.setOnClickListener {
            val orderId = dbHelper.checkout(cartItems, khid, txtaddress.text.toString(), "")
            if (orderId.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Order placed successfully! Order ID: $orderId",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to place order. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return view
    }

    private fun calculateAndDisplayTotals() {
        var totalAmount = 0.0
        val shippingFee = 5000.0 // Phí giao hàng cố định

        // Tính tổng tiền dựa trên giỏ hàng
        for (item in cartItems) {
            totalAmount += item.first.gia * item.second
        }

        val grandTotal = totalAmount + shippingFee

        // Cập nhật giao diện người dùng
        txtTotalAmount.text = "${totalAmount}đ"
        txtShippingFee.text = "${shippingFee}đ"
        txtGrandTotal.text = "${grandTotal}đ"
    }
}