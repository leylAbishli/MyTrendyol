package com.example.mytrendyol.presentation.ui.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FragmentShoppingCardBinding
import com.example.mytrendyol.presentation.ui.activity.MainActivity
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
        backView()

    }

    private fun addInfo() {
        binding.buttonConfirm.setOnClickListener {
            val name = binding.userNametxt.text.toString()
            val email = binding.epostatxt.text.toString()
            val countryCodeStr = binding.countryCodetxt.text.toString()
            val phoneNumberStr = binding.numbertxt.text.toString()
            val country = binding.countrytxt.text.toString()
            val region = binding.regiontxt.text.toString()
            val address = binding.addresstxt.text.toString()
            val cardNumberStr = binding.cardNumberstxt.text.toString()
            val monthStr = binding.monthtxt.text.toString()
            val yearStr = binding.yeartxt.text.toString()
            val cvvStr = binding.cvvtxt.text.toString()

            // Check if any of the required fields are empty
            if (name.isBlank() || email.isBlank() || countryCodeStr.isBlank() ||
                phoneNumberStr.isBlank() || country.isBlank() || region.isBlank() ||
                address.isBlank() || cardNumberStr.isBlank() || monthStr.isBlank() ||
                yearStr.isBlank() || cvvStr.isBlank()
            ) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate numeric fields
            try {
                val countryCode = countryCodeStr.toInt()
                val phoneNumber = phoneNumberStr.toInt()
                val cardNumber = cardNumberStr.toInt()
                val month = monthStr.toInt()
                val year = yearStr.toInt()
                val cvv = cvvStr.toInt()

                // If all validations pass, proceed with saving user data
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
                Toast.makeText(requireContext(), "Siparişiniz başarıyla alınmıştır", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                // Handle invalid numeric values
                Toast.makeText(requireContext(), "Lütfen geçerli sayısal değerler girin", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Handle any other unexpected errors
                Toast.makeText(requireContext(), "Bir hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(false)
    }
    private fun backView(){
        binding.imageView7.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingCardFragment_to_shoppingFragment)
        }
    }
}