package com.example.mytrendyol.signup

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth) :ViewModel(){

    private val _loginUser = MutableLiveData<FirebaseUser>()
    val loginUser: LiveData<FirebaseUser> = _loginUser

    fun checkLogin(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                        onSuccess(task.exception?.message ?: "Please verify your email address")
                    }
                    val user = auth.currentUser
                    val userId = user?.uid
                    val db = FirebaseFirestore.getInstance()
                    val userDoc = db.collection("users").document(userId!!)
                    val userData = hashMapOf(
                        "email" to email,
                        "password" to password,
                    )

                    userDoc.set(userData)
                        .addOnSuccessListener {
                            Log.d(TAG, "Kullanıcı Firestore'a başarıyla eklendi.")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Kullanıcı Firestore'a eklenirken hata oluştu.", e)
                        }

                } else {
                    onFailure(task.exception?.message ?: "Sign up failed")
                }


            }
    }
    fun checkEmailVerification(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (user.isEmailVerified) {
                    onSuccess()
                } else {
                    onFailure()
                }
            } else {
                onFailure()
            }
        }
    }
}