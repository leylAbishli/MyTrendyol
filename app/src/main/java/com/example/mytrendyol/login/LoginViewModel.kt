package com.example.mytrendyol.login

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private val _loggedInUser = MutableLiveData<FirebaseUser?>()
    val loggedInUser: LiveData<FirebaseUser?> get() = _loggedInUser
    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> get() = _snackbarMessage

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful ) {
                _loggedInUser.value = auth.currentUser
            }else{
                _snackbarMessage.value = "E-Posta adresiniz ve/ve ya şifreniz yanlış"
            }
        }

    }

}