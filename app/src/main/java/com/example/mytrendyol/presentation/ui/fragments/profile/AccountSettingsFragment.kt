package com.example.mytrendyol.presentation.ui.fragments.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.R
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentAccountSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

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
        btnClick()

        val languages = arrayOf("Azerice", "English")
        val builder = AlertDialog.Builder(requireContext())

        builder.setItems(languages) { dialog, which ->
            when (which) {
                0 -> setLocale("az")
                1 -> setLocale("en") 
            }
            dialog.dismiss()
            reStartFragment()
        }
        val dialog = builder.create()
        binding.txtlanguage.setOnClickListener {
            dialog.show()

        }

    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    private fun reStartFragment(){
        val navController=findNavController()
        navController.popBackStack()
        navController.navigate(R.id.accountSettingsFragment)
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

    private fun btnClick(){
        binding.txtlocation.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingsFragment_to_addressFragment)

        }
        binding.txtcards.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingsFragment_to_cardInformationFragment)
        }
    }



}