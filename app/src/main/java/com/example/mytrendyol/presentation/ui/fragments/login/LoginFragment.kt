package com.example.mytrendyol.presentation.ui.fragments.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FragmentLoginBinding
import com.example.mytrendyol.presentation.ui.viewmodels.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        emailFocusListener()
        checkLogin()
        signUp()
        bottomNavigation()
        resetPsw()
    }
    private fun checkLogin() {
        binding.btnlogin.setOnClickListener {
            loginViewModel.login(
                binding.txtepostalgn.text.toString(),
                binding.txtpswlog.text.toString()
            )
        }
        loginViewModel.loggedInUser.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        loginViewModel.snackbarMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp() {
        binding.txtsignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(false)
    }
    private fun emailFocusListener() {
        binding.txtepostalgn.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.lgnEpst.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {

        val emailText = binding.txtepostalgn.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Lütfen doğru e-posta adresi giriniz"
        }
        return null
    }



    private fun resetPsw(){
        binding.textPswForget.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
    }
}