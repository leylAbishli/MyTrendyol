package com.example.mytrendyol.presentation.ui.viewmodels.detailed.productInfo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.detailed.ProductInfoModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductInfoViewModel  @Inject constructor(private val db: FirebaseFirestore): ViewModel() {

    private val _productInfo = MutableLiveData<List<ProductInfoModel>>()
    val productInfo: LiveData<List<ProductInfoModel>> get() = _productInfo

    fun getProductInfo(context: Context, productId:String?) {
        db.collection("productInfo")
            .whereEqualTo("id",productId)
            .get()
            .addOnSuccessListener { result ->
                val productInfoList = mutableListOf<ProductInfoModel>()
                for (document in result) {
                    val info = ProductInfoModel(
                        color = document.getString(ConstValues.COLOR_FIELD) ?: "",
                        size = document.getString(ConstValues.SIZE_FIELD) ?: "",
                        material = document.getString(ConstValues.MATERIAL_FIELD) ?: "",
                        sealer = document.getString(ConstValues.SEALER_FIELD) ?: ""
                    )
                    productInfoList.add(info)
                }
                _productInfo.value = productInfoList
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