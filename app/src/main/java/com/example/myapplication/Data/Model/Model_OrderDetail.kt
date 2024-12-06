package com.example.myapplication.Data.Model

import java.io.Serializable

class Model_OrderDetail (
    val orderdetailid: String,
    val orderid: String,
    val productid: String,
    val quantity: Int,
    val total: Double
):Serializable