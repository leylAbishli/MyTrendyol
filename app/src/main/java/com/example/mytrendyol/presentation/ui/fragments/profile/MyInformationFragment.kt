package com.example.mytrendyol.presentation.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrendyol.databinding.FragmentMyInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class MyInformationFragment : Fragment() {
    private lateinit var binding: FragmentMyInformationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyInformationBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUptade.setOnClickListener {
            updateUserInformation()
        }
    }

    private fun updateUserInformation() {
        val currentUser = auth.currentUser
//        val newUserName = binding.userName.text.toString().trim()
//
//        val profileUpdates = UserProfileChangeRequest.Builder()
//            .setDisplayName(newUserName)
//            .build()
//
//        currentUser?.updateProfile(profileUpdates)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                } else {
//                    Log.e("myyytag","basarisiz ${updateUserInformation()}")
//                }
//            }
   }
}