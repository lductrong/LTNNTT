package com.example.myapplication.Data.Mode

import java.io.Serializable

public class Model_customer(
        val customerid: String,
        val namecus: String,
        val addresscus: String,
        val daycus: String,
        val gender: String,
        val phonecus: String,
        val notecus: String,
        val idaccount: String
    ): Serializable