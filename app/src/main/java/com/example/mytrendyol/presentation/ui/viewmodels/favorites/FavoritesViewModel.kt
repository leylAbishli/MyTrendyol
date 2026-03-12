package com.example.mytrendyol.presentation.ui.viewmodels.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val auth:FirebaseAuth,private val db:FirebaseFirestore):ViewModel() {
    private val _favoriteProducts = MutableLiveData<ArrayList<MainModel>?>()
    val favoriteProducts: LiveData<ArrayList<MainModel>?> = _favoriteProducts
    private val _favoriteProducts2 = MutableLiveData<ArrayList<MainModel>>()
    val favoriteProducts2: LiveData<ArrayList<MainModel>> = _favoriteProducts2


    fun mySaves() {
        val mySaves = ArrayList<String>()
        db.collection("likes").document(auth.currentUser!!.uid)
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
                            readSavesForFlashProduct(mySaves)

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
                            val id = document.get(ConstValues.ID_FIELD) as String
                            if (mySaves.contains(id)) {
                                val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                                    document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val sizeList = if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                                    document.get(ConstValues.SIZE_FIELD) as ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val productName = document.get(ConstValues.PRODUCT_NAME_FIELD) as String
                                val description = document.get(ConstValues.DESCRIPTION_FIELD) as String
                                val category = document.get(ConstValues.CATEGORY_FIELD) as String
                                val imageUrl = imageList
                                val price=document.get(ConstValues.PRICE_FIELD) as Double
                                val size=sizeList
                                val quantity=document.get(ConstValues.QUANTITY_FIELD) as Long
                                val post = MainModel(id, productName,price, description, category,imageUrl,size,quantity)
                                savepostList.add(post)

                            }

                        } catch (e: Exception) {

                            Log.e("saves_error", e.localizedMessage!!)
                        }
                    }
                    _favoriteProducts.postValue(savepostList)
                }
            }
        }


    }
    private fun readSavesForFlashProduct(mySaves: ArrayList<String>) {

        db.collection("flashProducts").addSnapshotListener { value, error ->
            if (error != null) {
                error.localizedMessage?.let { Log.e("posts", it) }
            } else {
                if (value != null) {
                    val savepostList = ArrayList<MainModel>()
                    for (document in value.documents) {
                        try {
                            val id = document.get(ConstValues.ID_FIELD) as String
                            if (mySaves.contains(id)) {
                                val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                                    document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val sizeList = if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                                    document.get(ConstValues.SIZE_FIELD) as ArrayList<*>
                                } else {
                                    ArrayList<Any>()
                                }
                                val productName = document.get(ConstValues.PRODUCT_NAME_FIELD) as String
                                val description = document.get(ConstValues.DESCRIPTION_FIELD) as String
                                val category = document.get(ConstValues.CATEGORY_FIELD) as String
                                val imageUrl = imageList
                                val price=document.get(ConstValues.PRICE_FIELD) as Double
                                val size=sizeList
                                val quantity=document.get(ConstValues.QUANTITY_FIELD) as Long

                                val post = MainModel(id, productName,price, description, category, imageUrl,size,quantity)
                                savepostList.add(post)
                            }

                        } catch (e: Exception) {

                            Log.e("saves_error", e.localizedMessage!!)
                        }
                    }
                    _favoriteProducts2.postValue(savepostList)
                }
            }
        }


    }
}