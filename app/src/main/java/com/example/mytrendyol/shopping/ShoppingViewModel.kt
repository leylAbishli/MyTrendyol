//package com.example.mytrendyol.shopping
//
//import android.content.Context
//import android.widget.Toast
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.mytrendyol.main.models.MainModel
//import com.google.firebase.firestore.FirebaseFirestore
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class ShoppingViewModel @Inject constructor():ViewModel() {
//
//        private val db = FirebaseFirestore.getInstance()
//
//        private val _products = MutableLiveData<List<MainModel>>()
//        val products: LiveData<List<MainModel>> get() = _products
//
//
//        fun getProduct(context: Context) {
//            db.collection("products")
//                .get()
//                .addOnSuccessListener { result ->
//                    val productList = mutableListOf<MainModel>()
//                    for (document in result) {
//                        val sizeList = if (document.get("size") is ArrayList<*>) {
//                            document.get("size") as ArrayList<*>
//                        } else {
//                            ArrayList<Any>() // Varsayılan bir ArrayList oluşturabilirsiniz
//                        }
//                        val product = MainModel(
//                            id = document.getString("id") ?: "",
//                            productName = document.getString("productName") ?: "",
//                            description = document.getString("description") ?: "",
//                            category = document.getString("category") ?: "",
//                            price = document.getDouble("price") ?: 0.0,
//                            imageUrl = document.getString("imageUrl") ?: "",
//                            size = sizeList
//
//
//                        )
//                        productList.add(product)
//                    }
//                    _products.value = productList
//                }
//                .addOnFailureListener { exception ->
//                    Toast.makeText(
//                        context,
//                        "Error getting products: ${exception.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()// Toast.makeText()
//                }
//
//        }
//
//}