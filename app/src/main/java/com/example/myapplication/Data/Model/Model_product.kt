package com.example.myapplication.Data.Model

import java.io.Serializable

data class Model_product(
    val productid: String,
    val masp: String,
    val tensp: String,
    val gia: Double,
    val maloai: String,
    val donvi: String,
    val img: String
):Serializable