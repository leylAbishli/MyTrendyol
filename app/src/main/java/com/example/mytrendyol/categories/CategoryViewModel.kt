package com.example.mytrendyol.categories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _categoryList = MutableLiveData<List<CategoryModel>>()
    val categoryList: LiveData<List<CategoryModel>> get() = _categoryList
    fun getCategoryForCategories(context: Context) {
        db.collection("categoriesforcategory").get().addOnSuccessListener { result ->
            val catList = mutableListOf<CategoryModel>()
            for (document in result) {
                val categoryProduct = CategoryModel(
                    imageUrl = document.getString("imageUrl") ?: "",
                    categoryName = document.getString("categoryName" ?: "")
                )
                catList.add(categoryProduct)
            }
            _categoryList.value = catList
        }.addOnFailureListener { exception ->
            Toast.makeText(
                context,
                "Error getting advertisement Image: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()// Toast.makeText()
        }

    }

}