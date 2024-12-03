package com.example.myapplication.Fragment_User.Wishlist

import DatabaseHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.Data.Model.Model_cart
import com.example.myapplication.Fragment_User.Cart.Adapter_Cart
import com.example.myapplication.R

class Fragment_wishlist : Fragment() {
    private lateinit var rvWishlist: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private var accountId: String = ""
    private var khid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.user_wishlist, container, false)

        // Khởi tạo cơ sở dữ liệu
        dbHelper = DatabaseHelper(requireContext())

        // Lấy account ID từ arguments
        arguments?.let {
            accountId = it.getString("account_id").toString()
        }

        // Lấy ID khách hàng
        khid = dbHelper.getIDKH(accountId)

        // Cấu hình RecyclerView
        rvWishlist = view.findViewById(R.id.rv_wishlist)
        rvWishlist.layoutManager = LinearLayoutManager(requireContext())

        // Lấy danh sách sản phẩm yêu thích
        val wishlist = dbHelper.getWishList(khid)

        // Tạo Adapter và gán dữ liệu
        val adapter = Adapter_wishlist(
            wishlist,
            onDeleteClick = { product ->
                // Xóa sản phẩm khỏi wishlis
                if (dbHelper.deleteFromWishlist(khid, product.productid)) {
                    wishlist.remove(product)
                    rvWishlist.adapter?.notifyDataSetChanged()
                    Toast.makeText(
                        requireContext(),
                        "${product.tensp} đã xóa khỏi danh sách yêu thích",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Lỗi khi xóa ${product.productid} khỏi danh sách yêu thích",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            onAddToCartClick = { product, quantity ->
                if (quantity > 0) {

                    val cart = Model_cart(
                        "", // ID cart tự động sinh nếu cần
                        quantity.toDouble(),
                        product.gia * quantity, // Tính tổng tiền
                        product.productid, // ID sản phẩm
                        khid, // ID khách hàng
                        product.img // Hình ảnh sản phẩm
                    )

                    val status = dbHelper.addCart(cart)
                    if (status > -1) {
                        Toast.makeText(requireContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Không thể thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            }
        )
        rvWishlist.adapter = adapter

        return view
    }
}