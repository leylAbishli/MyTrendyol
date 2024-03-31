package com.example.mytrendyol.domain.repositoryy.main

import android.util.Log
import android.widget.Toast
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel
import com.example.mytrendyol.presentation.ui.models.main.CategoryModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.example.mytrendyol.utils.ConstValues
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImp @Inject constructor(private val db: FirebaseFirestore) {

     suspend fun getProducts(): List<MainModel> {
        return try {
            val result = db.collection("products").get().await()
            val productList = mutableListOf<MainModel>()
            for (document in result) {
                val imageList = if (document.get(ConstValues.IMAGE_URL_FIELD) is ArrayList<*>) {
                    document.get(ConstValues.IMAGE_URL_FIELD) as ArrayList<*>
                } else {
                    ArrayList<Any>()
                }
                val product = MainModel(
                    id = document.getString(ConstValues.ID_FIELD) ?: "",
                    productName = document.getString(ConstValues.PRODUCT_NAME_FIELD) ?: "",
                    description = document.getString(ConstValues.DESCRIPTION_FIELD) ?: "",
                    category = document.getString(ConstValues.CATEGORY_FIELD) ?: "",
                    price = document.getDouble(ConstValues.PRICE_FIELD) ?: 0.0,
                    imageUrl = imageList,
                    quantity = document.get(ConstValues.QUANTITY_FIELD) as Long
                )
                productList.add(product)
            }
            productList
        } catch (e: Exception) {
            // Hata durumunda gerçekleştirilecek işlemler
            emptyList() // Hata durumunda boş bir liste döndürülebilir veya throw edilebilir
        }
    }

     suspend fun getFlashProducts(): List<FlashProductModel> {
        return try {
            val result = db.collection("flashProducts").get().await()
            val productList = mutableListOf<FlashProductModel>()
            for (document in result) {
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
                    quantity = document.get(ConstValues.QUANTITY_FIELD) as Long

                )
                Log.e("mytag", "${imageList[0]}")
                Log.e("mytag", "${imageList.size}")
                productList.add(product)
            }
            productList

        } catch (e: Exception) {
            // Hata durumunda gerçekleştirilecek işlemler
            emptyList() // Hata durumunda boş bir liste döndürülebilir veya throw edilebilir
        }
    }

     suspend fun getAdvertisementImages(): List<AdvertisementModel> {
        return try {
            val result = db.collection("advertisementList").get().await()
            val imageList = mutableListOf<AdvertisementModel>()
            for (document in result) {
                val advertProducts = AdvertisementModel(
                    imageUrl = document.getString(ConstValues.IMAGE_URL_FIELD) ?: ""
                )
                imageList.add(advertProducts)
            }
            imageList

        } catch (e: Exception) {
            // Hata durumunda gerçekleştirilecek işlemler
            emptyList() // Hata durumunda boş bir liste döndürülebilir veya throw edilebilir
        }
    }

     suspend fun getCategories(): List<CategoryModel> {
        return try {
            val result = db.collection("categories").get().await()
            val categoryList = mutableListOf<CategoryModel>()
            for (document in result) {
                val category = CategoryModel(
                    id = document.getString(ConstValues.ID_FIELD),
                    name = document.getString(ConstValues.NAME_FIELD) ?: "",
                )
                categoryList.add(category)
            }
            categoryList

        } catch (e: Exception) {
            // Hata durumunda gerçekleştirilecek işlemler
            emptyList() // Hata durumunda boş bir liste döndürülebilir veya throw edilebilir
        }
    }
}

