package com.example.mytrendyol.presentation.ui.fragments.signUp

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FragmentSignUpBinding
import com.example.mytrendyol.presentation.ui.viewmodels.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        bottomNavigation()
        setSignUp()
        emailFocusListener()
        passwordFocusListener()
        alreadyLogin()
    }


    private fun emailFocusListener() {
        binding.emailedttxtsign.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val errorMessage = validEmail()
                if (errorMessage != null) {
                    binding.edtEmailSignUp.helperText = errorMessage
                }
            }
        }
    }

    private fun validEmail(): String? {

        val emailText = binding.emailedttxtsign.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Lütfen doğru e-posta adresi giriniz"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.txtpswsignup.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val errorMessage = validPassword()
                if (errorMessage != null) {
                    binding.edtPswSignUp.helperText = errorMessage
                }
            }
        }
    }

    private fun validPassword(): String? {

        val passwordText = binding.txtpswsignup.text.toString()
        if (passwordText.length < 7) {
            return "En az 7 karekter olmalı"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Mutlaka harf içermelidir"
        }
        if (!passwordText.matches(".*[0-9].*".toRegex())) {
            return "Mutlaka rakam içermelidir"
        }
        return null
    }


    private fun setSignUp() {
        binding.btnSignN.setOnClickListener {
            if (checkBox()) {
                val email = binding.emailedttxtsign.text.toString()
                val password = binding.txtpswsignup.text.toString()

                val emailError = validEmail()
                val passwordError = validPassword()

                if (emailError != null && passwordError != null) {
                    binding.edtEmailSignUp.helperText = emailError
                    binding.edtPswSignUp.helperText = passwordError
                } else {
                    lifecycleScope.launch {
                        signUpViewModel.checkSignUp(
                            email,
                            password,
                            {
                                signUpViewModel.checkEmailVerification({
                                    Toast.makeText(
                                        requireContext(),
                                        "Sign up successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }, {
                                    Toast.makeText(
                                        requireContext(),
                                        "Please verify your email address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                            }, { errorMessage ->
                                // On failure
                                Toast.makeText(
                                    requireContext(),
                                    errorMessage,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            })

                        val duration= measureTimeMillis {
                            var check=false
                            repeat(200) {
                                if(check){
                                    return@repeat
                                }
                                 signUpViewModel.checkEmailVerification(
                                     {
                                         check=true
                                     findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)

                                     },{}
                                 )
                                delay(1000)
                            }
                        }

                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Sonuncu Secilmek Zorundadir",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

//        val user=FirebaseAuth.getInstance().currentUser
//
//        if(user!=null){
//            if(user.isEmailVerified){
//                findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
//            }
//
//        }


    }


    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(false)
    }

    private fun checkBox(): Boolean {

        val checkBox3 = binding.checkBox3
        if (checkBox3.isChecked) {
            Toast.makeText(
                requireContext(),
                "Sonuncu Secildi",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Sonuncu Secilmek Zorundadir",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }


    private fun alreadyLogin() {
        binding.textVievUye2.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }
}



