package com.example.myapplication.Data.Model

import java.io.Serializable

class Model_Order (
    val order_id: String,
    val orderday: String,
    val customerid : String,
    var thanhtien: Double,
    val address: String,
    val id_status: String
):Serializable