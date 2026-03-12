package com.example.mytrendyol.presentation.ui.viewmodels.detailed

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(private val db: FirebaseFirestore) : ViewModel() {
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
                    val sizeList = if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                        document.get(ConstValues.SIZE_FIELD) as ArrayList<*>
                    } else {
                        ArrayList<String>()
                    }
                    val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                        document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                    } else {
                        ArrayList<String>()
                    }
                    val product = MainModel(
                        id = document.getString(ConstValues.ID_FIELD) ?: "",
                        productName = document.getString(ConstValues.PRODUCT_NAME_FIELD) ?: "",
                        description = document.getString(ConstValues.DESCRIPTION_FIELD) ?: "",
                        category = document.getString(ConstValues.CATEGORY_FIELD) ?: "",
                        price = document.getDouble(ConstValues.PRICE_FIELD) ?: 0.0,
                        imageUrl = imageList,
                        size = sizeList,
                        quantity = document.get(ConstValues.QUANTITY_FIELD) as Long

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
                    val sizeList = if (document.get(ConstValues.SIZE_FIELD) is ArrayList<*>) {
                        document.get(ConstValues.SIZE_FIELD) as ArrayList<*>
                    } else {
                        ArrayList<Any>()
                    }
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
                        size = sizeList,
                        quantity = document.get(ConstValues.QUANTITY_FIELD) as Long
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