package com.example.mytrendyol.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.main.models.AdvertisementModel
import com.example.mytrendyol.main.models.CategoryModel
import com.example.mytrendyol.main.models.FlashProductModel
import com.example.mytrendyol.main.models.MainModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _imageResource = MutableLiveData<Int>()
    val imageResource: LiveData<Int> = _imageResource

    private val _products = MutableLiveData<List<MainModel>>()
    val products: LiveData<List<MainModel>> get() = _products

    private val _flashProducts = MutableLiveData<List<FlashProductModel>>()
    val flashProducts: LiveData<List<FlashProductModel>> get() = _flashProducts

    private val _advertList = MutableLiveData<List<AdvertisementModel>>()
    val advertList: LiveData<List<AdvertisementModel>> get() = _advertList

    private val _likedProducts = MutableLiveData<Set<String>>()
    val likedProducts: LiveData<Set<String>> = _likedProducts




    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> get() = _categories


    fun getProduct(context: Context) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<MainModel>()
                for (document in result) {
                    val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                        document.get("imageUrl") as ArrayList<*>
                    } else {
                        ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                    }
                    val product = MainModel(
                        id = document.getString("id") ?: "",
                        productName = document.getString("productName") ?: "",
                        description = document.getString("description") ?: "",
                        category = document.getString("category") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        imageUrl = imageList


//                            id = 1,
//                    productName = "Ürün Adı",
//                    description = "Ürün Açıklaması",
//                    price = 10.99,
//                    imageUrl = "",
//                    isLiked = false

                    )
                    productList.add(product)
                }
                _products.value = productList
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Error getting products: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()// Toast.makeText()
            }

    }

    fun getFlashProduct(context: Context) {
        db.collection("flashProducts")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<FlashProductModel>()
                for (document in result) {
                    val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                        document.get("imageUrl") as ArrayList<*>
                    } else {
                        ArrayList<Any>()
                    }
                    val product = FlashProductModel(
                        id = document.getString("id") ?: "",
                        productName = document.getString("productName") ?: "",
                        description = document.getString("description") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        category = document.getString("category") ?: "",
                        imageUrl = imageList


                    )
                    Log.e("mytag","${imageList[0]}")
                    Log.e("mytag","${imageList.size}")
                    productList.add(product)
                }
                _flashProducts.value = productList
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Error getting products: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()// Toast.makeText()
            }

    }

    fun getAdvertisementImage(context: Context) {
        db.collection("advertisementList").get().addOnSuccessListener { result ->
            val imageList = mutableListOf<AdvertisementModel>()
            for (document in result) {
                val advertProducts = AdvertisementModel(
                    imageUrl = document.getString("imageUrl") ?: ""
                )
                imageList.add(advertProducts)
            }
            _advertList.value = imageList
        }.addOnFailureListener { exception ->
            Toast.makeText(
                context,
                "Error getting advertisement Image: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()// Toast.makeText()
        }

    }

    fun getCategories() {
        db.collection("categories").get().addOnSuccessListener { querySnapShot ->
            val categoryList = mutableListOf<CategoryModel>()
            for (document in querySnapShot.documents) {
                val category = CategoryModel(
                    id = document.id,
                    name = document.getString("name") ?: "",
                )
                categoryList.add(category)
            }
            _categories.value = categoryList
        }
    }




}


