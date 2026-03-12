package com.example.mytrendyol.presentation.ui.viewmodels.shopping

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.shopping.UserShopModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingCardViewModel @Inject constructor(private val db: FirebaseFirestore,private val auth: FirebaseAuth) : ViewModel() {

    private val _info = MutableLiveData<List<UserShopModel>>()
    val info: LiveData<List<UserShopModel>> get() = _info


    fun saveUserData(
        name: String,
        cardNumber: Int,
        countryCode: Int,
        phoneNumber: Int,
        email: String,
        address: String,
        country: String,
        region: String,
        cvv: Int,
        year: Int,
        month: Int
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userOrderInfo = hashMapOf(
                "name" to name,
                "address" to address,
                "phoneNumber" to phoneNumber,
                "cardNumber" to cardNumber,
                "country" to country,
                "countryCode " to countryCode,
                "email" to email,
                "region" to region,
                "cvv" to cvv,
                "year" to year,
                "month" to month
            )


            db.collection("usersShopInfo").document(userId).set(userOrderInfo)
                .addOnSuccessListener {

                }
                .addOnFailureListener { e ->

                }
        }
    }

    fun getUserInfo(context: Context) {
        db.collection("usersShopInfo")
            .get()
            .addOnSuccessListener { result ->
                val infoList = mutableListOf<UserShopModel>()
                for (document in result) {
                    val info = UserShopModel(
                        country = document.getString(ConstValues.COUNTRY_FIELD) ?: "",
                        region = document.getString(ConstValues.REGION_FIELD) ?: "",
                        address = document.getString(ConstValues.ADDRESS_FIELD) ?: "",
                        name = document.getString(ConstValues.NAME_FIELD) ?: "",
                        phoneNumber = document.get(ConstValues.PHONE_NUMBER_FIELD) ?: 0.0,
                        cartNumber = document.get(ConstValues.CARD_NUMBER_FIELD) ?: 0.0
                    )
                    infoList.add(info)
                }
                _info.value = infoList
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