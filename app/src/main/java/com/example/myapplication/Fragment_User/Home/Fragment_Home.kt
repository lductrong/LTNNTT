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
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Data.Model.Model_product
import com.example.myapplication.R
import android.text.TextWatcher
import android.widget.Toast
import android.text.Editable



class Fragment_Home : Fragment() {
private lateinit var rv_categories: RecyclerView
private lateinit var rv_product: RecyclerView
private lateinit var dbHelper: DatabaseHelper
private lateinit var img_product: ImageView
private lateinit var btn_detail: Button
private lateinit var txtaccount: TextView

private  lateinit var btnFilter: Button
private lateinit var btnSort: Button
private lateinit var edtSearch: EditText

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
        val view = inflater.inflate(R.layout.user_home, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Lấy các tham chiếu từ giao diện
        rv_categories = view.findViewById(R.id.rv_categories)
        rv_product = view.findViewById(R.id.rv_product)
        viewPager = view.findViewById(R.id.viewPager)
        btnSort = view.findViewById(R.id.filter)
        btnFilter = view.findViewById(R.id.btnsort)
        edtSearch = view.findViewById(R.id.estfind)

        arguments?.let {
            accountId = it.getString("account_id").toString()
        }

        setupViewPager()
        setupCategoryRecyclerView()
        setupProductRecyclerView()

        // Xử lý sự kiện click cho các nút
        btnSort.setOnClickListener { onSortClicked() }
        btnFilter.setOnClickListener { onFilterClicked() }
        // Thêm TextWatcher cho tìm kiếm
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                onSearch(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
    private fun setupViewPager() {
        adapter = ImageSliderAdapter(imageList)
        viewPager.adapter = adapter
        autoScrollImages()
    }

    private fun setupCategoryRecyclerView() {
        rv_categories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val category = dbHelper.getAllProductType()
        val adapter = Adapter_category(category)
        rv_categories.adapter = adapter
    }

    private fun setupProductRecyclerView() {
        rv_product.layoutManager = GridLayoutManager(requireContext(), 2)
        val product = dbHelper.getAllproduct()
        val adapter = Adapter_product_user(product, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                val intent = Intent(requireContext(), Product_Detail::class.java)
                intent.putExtra("productname", model.tensp)
                intent.putExtra("productprice", model.gia)
                intent.putExtra("imgsource", model.img)
                intent.putExtra("productid", model.productid)
                intent.putExtra("productdes", model.donvi)
                intent.putExtra("account_id", accountId)
                startActivity(intent)
            }
        })
        rv_product.adapter = adapter
    }

    private fun onSortClicked() {
        // Giả lập hành động sắp xếp sản phẩm (sắp xếp giá tăng dần)
        val sortedProducts = dbHelper.getAllproduct().sortedBy { it.gia }
        val adapter = Adapter_product_user(sortedProducts, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                // Mở chi tiết sản phẩm
            }
        })
        rv_product.adapter = adapter
    }

    private fun onFilterClicked() {
        // Giả lập hành động lọc (lọc sản phẩm có giá trên 100,000)
        val filteredProducts = dbHelper.getAllproduct().filter { it.gia > 100000 }
        val adapter = Adapter_product_user(filteredProducts, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                // Mở chi tiết sản phẩm
            }
        })
        rv_product.adapter = adapter
    }
    private fun onSearch(query: String) {
        val allProducts = dbHelper.getAllproduct()
        if (allProducts.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Không có sản phẩm nào để tìm kiếm.", Toast.LENGTH_SHORT).show()
            return
        }

        val searchedProducts = allProducts.filter { it.tensp.contains(query, ignoreCase = true) }
        val adapter = Adapter_product_user(searchedProducts, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                openProductDetail(model)
            }
        })
        rv_product.adapter = adapter
    }

    private fun updateProductRecyclerView(products: List<Model_product>) {
        val adapter = Adapter_product_user(products, object : Adapter_product_user.OnClickListener {
            override fun onClick(position: Int, model: Model_product) {
                openProductDetail(model)
            }
        })
        rv_product.adapter = adapter
    }

    private fun openProductDetail(model: Model_product) {
        val intent = Intent(requireContext(), Product_Detail::class.java).apply {
            putExtra("productname", model.tensp)
            putExtra("productprice", model.gia)
            putExtra("imgsource", model.img)
            putExtra("productid", model.productid)
            putExtra("productdes", model.donvi)
            putExtra("account_id", accountId)
        }
        startActivity(intent)
    }

//    private fun onSearchClicked(query: String) {
//        // Giả lập tìm kiếm sản phẩm theo tên
//        val searchedProducts = dbHelper.getAllproduct().filter { it.tensp.contains(query, true) }
//        val adapter = Adapter_product_user(searchedProducts, object : Adapter_product_user.OnClickListener {
//            override fun onClick(position: Int, model: Model_product) {
//                // Mở chi tiết sản phẩm
//            }
//        })
//        rv_product.adapter = adapter
//    }

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