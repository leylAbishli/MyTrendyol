package com.example.mytrendyol.profile

import MyInformationFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class ProfileFragment : Fragment(){
    private lateinit var auth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseDb: FirebaseFirestore
    private var currentUser: FirebaseUser? = null
     private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLogin()
        accountSetting()
    }
    private fun setLogin(){
        val currentUser=auth.currentUser
        if(currentUser !=null){
            binding.emailText.text=currentUser.email
            binding.usernameText.text=currentUser.displayName
        }


    }

    private fun accountSetting(){
        binding.txtaccountSetting.setOnClickListener {
             findNavController().navigate(R.id.action_profileFragment_to_accountSettingsFragment)
        }
    }





}