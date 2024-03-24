package com.example.mytrendyol.presentation.ui.viewmodels.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrendyol.domain.usecasee.LoginUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loggedInUser = MutableLiveData<FirebaseUser?>()
    val loggedInUser: LiveData<FirebaseUser?> get() = _loggedInUser

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> get() = _snackbarMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = loginUseCase(email, password)
            if (user != null) {
                _loggedInUser.value = user
            } else {
                _snackbarMessage.value = "E-Posta adresiniz ve/veya şifreniz yanlış"
            }
        }
    }
}