package com.example.myapplication.Fragment_User.Home

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Model.Model_cart
import com.example.myapplication.Data.Model.Model_wishlist
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class Product_Detail : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var txt_productname : TextView;
    private lateinit var txt_rating : TextView;
    private lateinit var txt_des : TextView;
    private lateinit var txt_productprice : TextView;
    private lateinit var img_product : ImageView;
    private lateinit var imgv_back : ImageView;
    private lateinit var addwishlish : ImageView;
    private lateinit var btn_addcart : Button;
    private lateinit var txt_quantity : EditText;
    private  lateinit var imgshare: ImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var btn_decrease : Button;
    private lateinit var btn_increase : Button;
    private lateinit var text_quantity : TextView;

    private val REQUEST_PERMISSION_CODE = 100
    private var accountId: String = ""
    private var khid: String = ""
    private var id: String = ""
    private var productid: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.home_productdetail)
        dbHelper = DatabaseHelper(this)
        txt_productprice = findViewById(R.id.txt_productprice)
        img_product = findViewById(R.id.img_product)
        txt_productname = findViewById(R.id.txt_productname)
        txt_des = findViewById(R.id.txt_des)
        imgv_back = findViewById(R.id.imgv_back)
        txt_quantity = findViewById(R.id.txt_quantity)
        btn_addcart = findViewById(R.id.btn_addcart)
        addwishlish = findViewById(R.id.addwishlish)
        imgshare = findViewById(R.id.imgshare)
        btn_decrease = findViewById(R.id.button_decrease)
        btn_increase = findViewById(R.id.button_increase)
        text_quantity = findViewById(R.id.text_quantity)

        val productname = intent.getStringExtra("productname")
        val imgUriString = intent.getStringExtra("imgsource")
        productid = intent.getStringExtra("productid").toString()
        val productprice = intent.getDoubleExtra("productprice", 1.0)
        accountId = intent.getStringExtra("account_id").toString()
        val productdes = intent.getStringExtra("productdes").toString()
        khid = dbHelper.getIDKH(accountId)

        txt_productname.text = productname
        txt_productprice.text = productprice.toString()
        txt_des.text = productdes

        id = dbHelper.getIdProduct(productid)

        imgUriString?.let {
            Glide.with(this)
                .load(it)
                .into(img_product)
        }

        imgv_back.setOnClickListener {
            finish()
        }
        btn_addcart.setOnClickListener(View.OnClickListener {
            addCart();
        })
        addwishlish.setOnClickListener{
            addWishLish();
        }
        var quantity = 1;
        text_quantity.text = quantity.toString();
        btn_decrease.setOnClickListener({
            if (quantity > 0) { // Ngăn không cho giá trị nhỏ hơn 0
                quantity--
                text_quantity.text = quantity.toString();
            }
        })
        btn_increase.setOnClickListener({
            quantity++
            text_quantity.text = quantity.toString();
        })
        imgshare.setOnClickListener {
            val data: String = txt_productname.text.toString()
            if(data.isNotEmpty()){
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, data)
                sendIntent.type = "text/plain"
                val shareIntent = Intent.createChooser(sendIntent, "Send to:")
                startActivity(shareIntent)
            }else{
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addWishLish() {
        val wishlist = Model_wishlist("", productid, khid)
        val status = dbHelper.addWishList(wishlist)
        if(status > -1 ){
            Toast.makeText(this, "Them vao wishlist thanfh cong", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Khoong them vao wishlist duoc", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addCart() {
        val quantity = txt_quantity.text.toString().toDoubleOrNull()

        if(quantity != null && productid.isNotEmpty()){
            val cart = Model_cart("",quantity,txt_productprice.text.toString().toDouble()*quantity ,productid, khid , img_product.toString());
            val status = dbHelper.addCart(cart)
            if (status > -1){
                Toast.makeText(this, "Theem vao gio hang thanh cong", Toast.LENGTH_SHORT).show()
                txt_quantity.text.clear()

            }else{
                Toast.makeText(this, "Khong them vao gio hang duoc", Toast.LENGTH_SHORT).show()

            }

        }else{
            Toast.makeText(this, "điền đầy du thông tin", Toast.LENGTH_SHORT).show()
        }
    }

}

