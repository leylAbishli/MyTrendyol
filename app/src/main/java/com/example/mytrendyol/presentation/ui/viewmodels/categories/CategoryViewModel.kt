package com.example.mytrendyol.presentation.ui.viewmodels.categories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.categories.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(  private val db :FirebaseFirestore) : ViewModel() {
    private val _categoryList = MutableLiveData<List<CategoryModel>>()
    val categoryList: LiveData<List<CategoryModel>> get() = _categoryList
    fun getCategoryForCategories(context: Context) {
        db.collection("categoriesforcategory").get().addOnSuccessListener { result ->
            val catList = mutableListOf<CategoryModel>()
            for (document in result) {
                val categoryProduct = CategoryModel(
                    imageUrl = document.getString(ConstValues.IMAGE_URL_FIELD) ?: "",
                    categoryName = document.getString(ConstValues.CATEGORYNAME_FIELD) ?: ""
                )
                catList.add(categoryProduct)
            }
            _categoryList.value = catList
        }.addOnFailureListener { exception ->
            Toast.makeText(
                context,
                "Error getting advertisement Image: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}