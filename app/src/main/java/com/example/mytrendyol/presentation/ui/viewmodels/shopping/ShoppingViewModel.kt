package com.example.mytrendyol.presentation.ui.viewmodels.shopping

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.example.mytrendyol.utils.ConstValues
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _products = MutableLiveData<ArrayList<MainModel>>()
    val products: LiveData<ArrayList<MainModel>> get() = _products

    private val _flashProducts = MutableLiveData<ArrayList<FlashProductModel>>()
    val flashProducts: LiveData<ArrayList<FlashProductModel>> get() = _flashProducts

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
                            readSavesForFlash(mySaves)

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
                            val id = document.get(ConstValues.ID_FIELD) as? String
                            if (mySaves.contains(id)) {
                                val imageList =
                                    if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                                        document.get(ConstValues.IMAGE_URL_FIELD) as? ArrayList<*>
                                    } else {
                                        ArrayList<Any>()
                                    }
                                val sizeList =
                                    if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                                        document.get(ConstValues.SIZE_FIELD) as? ArrayList<*>
                                    } else {
                                        ArrayList<Any>()
                                    }
                                val productName =
                                    document.get(ConstValues.PRODUCT_NAME_FIELD) as? String
                                val description =
                                    document.get(ConstValues.DESCRIPTION_FIELD) as? String
                                val category = document.get(ConstValues.CATEGORY_FIELD) as? String
                                val imageUrl = imageList
                                val size = sizeList
                                val price = document.get(ConstValues.PRICE_FIELD) as? Double
                                val quantity = document.get(ConstValues.QUANTITY_FIELD) as Long
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

    private fun readSavesForFlash(mySaves: ArrayList<String>) {

        db.collection("flashProducts").addSnapshotListener { value, error ->
            if (error != null) {
                error.localizedMessage?.let { Log.e("posts", it) }
            } else {
                if (value != null) {
                    val savepostList = ArrayList<FlashProductModel>()
                    for (document in value.documents) {
                        try {
                            val id = document.get(ConstValues.ID_FIELD) as? String
                            if (mySaves.contains(id)) {
                                val imageList =
                                    if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                                        document.get(ConstValues.IMAGE_URL_FIELD) as? ArrayList<*>
                                    } else {
                                        ArrayList<Any>()
                                    }
                                val sizeList =
                                    if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                                        document.get(ConstValues.SIZE_FIELD) as? ArrayList<*>
                                    } else {
                                        ArrayList<Any>()
                                    }
                                val productName =
                                    document.get(ConstValues.PRODUCT_NAME_FIELD) as? String
                                val description =
                                    document.get(ConstValues.DESCRIPTION_FIELD) as? String
                                val category = document.get(ConstValues.CATEGORY_FIELD) as? String
                                val imageUrl = imageList
                                val size = sizeList
                                val price = document.get(ConstValues.PRICE_FIELD) as? Double
                                val quantity = document.get(ConstValues.QUANTITY_FIELD) as Long
                                val post = FlashProductModel(
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
                    _flashProducts.postValue(savepostList)
                }
            }
        }
    }

}