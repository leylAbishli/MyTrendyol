package com.example.mytrendyol.presentation.ui.viewmodels.shopping

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val db :FirebaseFirestore,
        private val auth : FirebaseAuth):ViewModel() {

        private val _products = MutableLiveData<ArrayList<MainModel>>()
        val products: LiveData<ArrayList<MainModel>> get() = _products

         private val _totalPrice = MutableLiveData(0.0)
         val totalPrice: LiveData<Double?> get() = _totalPrice


    fun mySaves() {

        val mySaves = ArrayList<String>()
        db.collection("shopping").document(auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    error.localizedMessage?.let { Log.e("Saves_Error", it) }
                } else {
                    try {
                        if (value != null) {
                            val data = value.data as HashMap<*, *>
                            for (savekeys in data) {
                                mySaves.add(savekeys.key as String)
                            }
                            readSaves(mySaves)
                           // readSavesForFlashProduct(mySaves)

                        }
                    } catch (e: Exception) {
                        e.localizedMessage?.let { Log.e("Saves_Error", it) }
                    }

                }


            }


    }
    private fun readSaves(mySaves: ArrayList<String>) {

        db.collection("products").addSnapshotListener { value, error ->
            if (error != null) {
                error.localizedMessage?.let { Log.e("posts", it) }
            } else {
                if (value != null) {
                    val savepostList = ArrayList<MainModel>()
                    for (document in value.documents) {
                        try {
                            val id = document.get("id") as? String
                            if (mySaves.contains(id)) {
                                val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                                    document.get("imageUrl") as? ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val sizeList = if (document.get("size") is ArrayList<*>) {
                                    document.get("size") as? ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val productName = document.get("productName") as? String
                                val description = document.get("description") as? String
                                val category = document.get("category") as? String
                                val imageUrl = imageList
                                val size = sizeList
                                val price = document.get("price") as? Double
                                val quantity = document.get("quantity") as Long
                                val post = MainModel(
                                    id,
                                    productName,
                                    price,
                                    description,
                                    category,
                                    imageUrl,
                                    size,
                                    quantity
                                )
                                savepostList.add(post)

                            }

                        } catch (e: Exception) {

                            Log.e("saves_error", e.localizedMessage!!)
                        }
                    }
                    _products.postValue(savepostList)
                }
            }
        }
    }
    private var totalAmount: Double = 0.0

    fun getTotalAmount(): Double {
        return totalAmount
    }

    fun addToTotalAmount(amount: Double) {
        totalAmount += amount
    }

    fun subtractFromTotalAmount(amount: Double) {
        totalAmount -= amount
    }

    fun resetTotalAmount() {
        totalAmount = 0.0
    }
}