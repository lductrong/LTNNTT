package com.example.myapplication.Fragment_User.Home

import DatabaseHelper
import ImageSliderAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R
class Fragment_Home : Fragment() {
private lateinit var rv_categories: RecyclerView
private lateinit var rv_product: RecyclerView
private lateinit var dbHelper: DatabaseHelper
private lateinit var img_product: ImageView
private lateinit var btn_detail: Button
private lateinit var txtaccount: TextView
    private var accountId: String = "null"
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageSliderAdapter
    private val imageList = listOf(
        "https://lybee.vn/wp-content/uploads/2022/07/Artboard-4.jpg",
        "https://file.hstatic.net/200000182297/file/sacc_3ac903271d5a4ea0b08e55159bbabfd0.jpg",
        "https://file.hstatic.net/200000182297/file/banner_web_15_02_23_bc417e51b3bb4665845c5e80fa268e1c.jpg"
    )
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.user_home, container, false)
        dbHelper = DatabaseHelper(requireContext())

        arguments?.let {
            accountId = it.getString("account_id").toString()
        }


        rv_categories = view.findViewById(R.id.rv_categories)
        rv_product = view.findViewById(R.id.rv_product)
        viewPager = view.findViewById(R.id.viewPager)
        adapter = ImageSliderAdapter(imageList)
        viewPager.adapter = adapter
        autoScrollImages()
        rv_categories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val category = dbHelper.getAllProductType()
        val adapter = Adapter_category(category)
        rv_categories.adapter =adapter

        rv_product.layoutManager = GridLayoutManager(requireContext(), 2)
        val product = dbHelper.getAllproduct()
        val adapter1 = Adapter_product_user(product, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                val intent = Intent(requireContext(), Product_Detail::class.java)
//                intent.setType("image/*")
//                intent.setAction(Intent.ACTION_GET_CONTENT)
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra("productname", model.tensp)
                intent.putExtra("productprice", model.gia)
                intent.putExtra("imgsource", model.img)
                intent.putExtra("productid", model.productid)
                intent.putExtra("productdes", model.donvi)
                intent.putExtra("account_id", accountId)
                 startActivity(intent)
            }
        })
        rv_product.adapter = adapter1


        return view
    }
    private fun autoScrollImages() {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // Change image every 3 seconds
            }
        }
        handler.post(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}