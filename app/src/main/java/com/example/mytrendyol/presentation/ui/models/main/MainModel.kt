package com.example.mytrendyol.presentation.ui.models.main

data class MainModel(
    val id:String?=null,
    val productName:String?=null,
    var price:Double?=null,
    val description:String?=null,
    val category:String?=null,
    var imageUrl: ArrayList<*>?=null,
    val size: ArrayList<*>? =null,
    var quantity: Long
)
