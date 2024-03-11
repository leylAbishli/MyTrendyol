package com.example.mytrendyol.detailed

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.main.models.FlashProductModel
import com.example.mytrendyol.main.models.MainModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _products = MutableLiveData<List<MainModel>>()
    val products: LiveData<List<MainModel>> get() = _products

    private val _flashProducts = MutableLiveData<List<FlashProductModel>>()
    val flashProducts: LiveData<List<FlashProductModel>> get() = _flashProducts
    fun getProduct(context: Context) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<MainModel>()
                for (document in result) {
                    val sizeList = if (document.get("size") is ArrayList<*>) {
                        document.get("size") as ArrayList<*>
                    } else {
                        ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                    }
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
                        imageUrl = imageList,
                        size = sizeList


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
                    val sizeList = if (document.get("size") is ArrayList<*>) {
                        document.get("size") as ArrayList<*>
                    } else {
                        ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                    }
                    val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                        document.get("imageUrl") as ArrayList<*>
                    } else {
                        ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                    }
                    val product = FlashProductModel(
                        id = document.getString("id") ?: "",
                        productName = document.getString("productName") ?: "",
                        description = document.getString("description") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        category = document.getString("category") ?: "",
                        imageUrl = imageList,
                        size = sizeList
                        )
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
}