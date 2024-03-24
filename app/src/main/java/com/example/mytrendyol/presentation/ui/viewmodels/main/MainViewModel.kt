package com.example.mytrendyol.presentation.ui.viewmodels.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel
import com.example.mytrendyol.presentation.ui.models.main.CategoryModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(   private val db : FirebaseFirestore) : ViewModel() {

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
                    val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                        document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                    } else {
                        ArrayList<Any>()
                    }
                    val product = MainModel(
                        id = document.getString(ConstValues.ID_FIELD) ?: "",
                        productName = document.getString(ConstValues.PRODUCT_NAME_FIELD) ?: "",
                        description = document.getString(ConstValues.DESCRIPTION_FIELD) ?: "",
                        category = document.getString(ConstValues.CATEGORY_FIELD) ?: "",
                        price = document.getDouble(ConstValues.PRICE_FIELD) ?: 0.0,
                        imageUrl = imageList,
                        quantity =document.get(ConstValues.QUANTITY_FIELD) as Long
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
                    val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                        document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                    } else {
                        ArrayList<Any>()
                    }
                    val product = FlashProductModel(
                        id = document.getString(ConstValues.ID_FIELD) ?: "",
                        productName = document.getString(ConstValues.PRODUCT_NAME_FIELD) ?: "",
                        description = document.getString(ConstValues.DESCRIPTION_FIELD) ?: "",
                        category = document.getString(ConstValues.CATEGORY_FIELD) ?: "",
                        price = document.getDouble(ConstValues.PRICE_FIELD) ?: 0.0,
                        imageUrl = imageList,
                        quantity =document.get(ConstValues.QUANTITY_FIELD) as Long

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
                    imageUrl = document.getString(ConstValues.IMAGE_URL_FIELD) ?: ""
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
                    id = document.getString(ConstValues.ID_FIELD),
                    name = document.getString(ConstValues.NAME_FIELD) ?: "",
                )
                categoryList.add(category)
            }
            _categories.value = categoryList
        }
    }




}


