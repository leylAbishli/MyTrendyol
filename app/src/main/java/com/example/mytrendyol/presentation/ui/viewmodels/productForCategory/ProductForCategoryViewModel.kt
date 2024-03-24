package com.example.mytrendyol.presentation.ui.viewmodels.productForCategory

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.productForCategory.HomeModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductForCategoryViewModel @Inject constructor(private val db : FirebaseFirestore):ViewModel() {

    private val _homeProducts=MutableLiveData<List<HomeModel>>()
    val homeProducts:LiveData<List<HomeModel>> get() = _homeProducts




    fun getHomeProduct(context: Context) {
        db.collection("homeProductsForCategory")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<HomeModel>()
                for (document in result) {
                    val product = HomeModel(
                        id = document.getString(ConstValues.ID_FIELD) ?: "",
                        name = document.getString(ConstValues.PRODUCT_NAME_FIELD) ?: "",
                        description = document.getString(ConstValues.DESCRIPTION_FIELD) ?: "",
                        price = document.getDouble(ConstValues.PRICE_FIELD) ?: 0.0,
                        imageUrl = document.getString(ConstValues.IMAGE_URL_FIELD)
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
                ).show()
            }

    }



}