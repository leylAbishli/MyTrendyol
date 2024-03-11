package com.example.mytrendyol.favorites

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.main.models.FlashProductModel
import com.example.mytrendyol.main.models.MainModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.reflect.Array
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor():ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

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
                            val id = document.get("id") as String
                            if (mySaves.contains(id)) {
                                val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                                    document.get("imageUrl") as ArrayList<*>
                                } else {
                                    ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                                }
                                val productName = document.get("productName") as String
                                val description = document.get("description") as String
                                val category = document.get("category") as String
                                val imageUrl = imageList
                                val price=document.get("price") as Double
                                val post = MainModel(id, productName,price, description, category,imageUrl)
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
                            val id = document.get("id") as String
                            if (mySaves.contains(id)) {
                                val imageList = if (document.get("imageUrl") is ArrayList<*>) {
                                    document.get("imageUrl") as ArrayList<*>
                                } else {
                                    ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
                                }
                                val productName = document.get("productName") as String
                                val description = document.get("description") as String
                                val category = document.get("category") as String
                                val imageUrl = imageList
                                val price=document.get("price") as Double
                                val post = MainModel(id, productName,price, description, category, imageUrl)
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