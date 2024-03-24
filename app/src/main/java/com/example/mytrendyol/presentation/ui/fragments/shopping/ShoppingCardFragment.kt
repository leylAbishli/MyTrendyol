package com.example.mytrendyol.presentation.ui.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentShoppingCardBinding
import com.example.mytrendyol.presentation.ui.viewmodels.shopping.ShoppingCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShoppingCardFragment : Fragment() {
    private lateinit var binding: FragmentShoppingCardBinding
    private val shoppingCardViewModel: ShoppingCardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCardBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val totalAmount = args?.getDouble("totalAmount") ?: 0.0
        val formattedTotalAmount = String.format("%.2f", totalAmount)
        binding.totalText.text = formattedTotalAmount
        bottomNavigation()
        addInfo()

    }

    private fun addInfo() {
        binding.buttonConfirm.setOnClickListener {
            val name = binding.userNametxt.text.toString()
            val email = binding.epostatxt.text.toString()
            val countryCode = binding.countryCodetxt.text.toString().toInt()
            val phoneNumber = binding.numbertxt.text.toString().toInt()
            val country = binding.countrytxt.text.toString()
            val region = binding.regiontxt.text.toString()
            val address = binding.addresstxt.text.toString()
            val cardNumber = binding.cardNumberstxt.text.toString().toInt()
            val month = binding.monthtxt.text.toString().toInt()
            val year = binding.yeartxt.text.toString().toInt()
            val cvv = binding.cvvtxt.text.toString().toInt()

            lifecycleScope.launch {
                shoppingCardViewModel.saveUserData(
                    name,
                    cardNumber,
                    countryCode,
                    phoneNumber,
                    email,
                    country,
                    region,
                    address,
                    month,
                    year,
                    cvv
                )
            }
        }
    }

    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(false)
    }
}