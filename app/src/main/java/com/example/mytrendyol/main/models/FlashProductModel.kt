package com.example.mytrendyol.main.models

import java.io.Serializable

data class FlashProductModel(
    val id:String?=null,
    val productName:String?=null,
    val price:Double?=null,
    val description:String?=null,
    val category:String?=null,
    val imageUrl: ArrayList<*>?=null,
    val size:ArrayList<*>?=null
): Serializable

