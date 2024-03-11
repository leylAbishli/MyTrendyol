package com.example.mytrendyol.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mytrendyol.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding:FragmentResetPasswordBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentResetPasswordBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPassword()
    }

    private fun resetPassword(){
       binding.btnresetPasword.setOnClickListener {
           val textResetPassword=binding.txtResetEmail.text.toString()
           auth.sendPasswordResetEmail(textResetPassword).addOnSuccessListener {
               Toast.makeText(context,"E-Postanızı Kontrol edin",Toast.LENGTH_SHORT).show()
           }.addOnFailureListener {
               Toast.makeText(context,"Şifre yenilenmede hata oldu",Toast.LENGTH_SHORT).show()
           }
       }
    }
}