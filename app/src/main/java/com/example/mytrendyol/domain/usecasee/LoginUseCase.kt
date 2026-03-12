package com.example.mytrendyol.domain.usecasee

import com.example.mytrendyol.domain.repositoryy.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase  @Inject constructor(private val userRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): FirebaseUser? {
        return userRepository.loginUser(email, password)
    }
}