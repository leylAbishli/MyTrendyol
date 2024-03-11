package com.example.mytrendyol.productForCategory

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.productForCategory.models.HomeModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductForCategoryViewModel @Inject constructor():ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _homeProducts=MutableLiveData<List<HomeModel>>()
    val homeProducts:LiveData<List<HomeModel>> get() = _homeProducts




    fun getHomeProduct(context: Context) {
        db.collection("homeProductsForCategory")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<HomeModel>()
                for (document in result) {
                    val product = HomeModel(
                        id = document.getString("id") ?: "",
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        imageUrl = document.getString("imageUrl") ?: ""



                    )
                    productList.add(product)
                }
                _homeProducts.value = productList
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