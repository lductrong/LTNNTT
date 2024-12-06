package com.example.myapplication.Activity

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DatabaseHelper(this)
        val txtussername = findViewById<EditText>(R.id.txtussername);
        val txtpassword = findViewById<EditText>(R.id.txtpassword);
        val btnlogin = findViewById<Button>(R.id.btnlogin);
        val scan_qr_button = findViewById<Button>(R.id.scan_qr_button);
        scan_qr_button.setOnClickListener {
            // Bắt đầu quét QR code khi nhấn nút
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Scan a QR code")
            integrator.setCameraId(0) // sử dụng camera sau
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(true)
            integrator.captureActivity = CaptureActivity::class.java
            integrator.initiateScan()
        }
        btnlogin.setOnClickListener {
            val txtussername = txtussername.text.toString();
            val txtpassword = txtpassword.text.toString();
            val accountId = dbHelper.getAccountIdByEmail(txtussername)
          if(dbHelper.checkUser(txtussername, txtpassword)) {
                val intent = Intent(this, UserActivity::class.java)
                intent.putExtra("account_id", accountId)
                startActivity(intent);
            }else if(txtussername == "admin" && txtpassword == "admin"){
              Toast.makeText(this, "Đăng nhập thành công: ", Toast.LENGTH_LONG).show()
              val intent = Intent(this, AdminActivity::class.java)
              startActivity(intent)
            }else  {
                Toast.makeText(this, "Tài khoaản mật khẩu khng đúng", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val link: String = "https://www.techopedia.com/"

        if (result != null) {
            if (result.contents == null) {
                // QR code không được quét
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show()
            } else if (result.contents == link) {
                // QR code đã được quét thành công và khớp với đường dẫn
                Toast.makeText(this, "Đăng nhập thành công: ", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            } else {
                // QR code đã được quét thành công nhưng không khớp với đường dẫn
                Toast.makeText(this, "QR code không khớp", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onLoginClick(view : View){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
