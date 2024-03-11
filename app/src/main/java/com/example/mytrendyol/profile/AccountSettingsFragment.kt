package com.example.mytrendyol.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.R
import com.example.mytrendyol.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentAccountSettingsBinding
import com.google.firebase.auth.FirebaseAuth

class AccountSettingsFragment : Fragment() {
    private lateinit var binding: FragmentAccountSettingsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAccountSettingsBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigation()
        click()
        setLogOut()

    }
    private fun click(){
          binding.txtmyiformation.setOnClickListener {
              findNavController().navigate(R.id.action_accountSettingsFragment_to_myInformationFragment)
          }
    }

    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(true)
    }


    private fun setLogOut() {
        binding.txlogout.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingsFragment_to_loginFragment)
            auth.signOut()

        }
    }


}