package com.example.mytrendyol.presentation.ui.fragments.main

import com.example.mytrendyol.ui.adapters.main.MainProductAdapter
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentMainBinding
import com.example.mytrendyol.presentation.ui.adapters.favorites.OnChangedListener
import com.example.mytrendyol.presentation.ui.viewmodels.main.MainViewModel
import com.example.mytrendyol.ui.adapters.main.MainAdvertisementAdapter
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.android.material.chip.Chip
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment(), OnChangedListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var productAdapter: MainProductAdapter
    private lateinit var flashAdapter: MainProductAdapter
    private lateinit var advertisementAdapter: MainAdvertisementAdapter
    private var advertList = listOf<AdvertisementModel>()
    private var productList = listOf<MainModel>()
    private var flashProductList = listOf<FlashProductModel>()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        firestore = Firebase.firestore
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigation()
        mainViewModel.getProduct(requireContext())
        mainViewModel.getFlashProduct(requireContext())
        mainViewModel.getAdvertisementImage(requireContext())
        mainViewModel.getCategories()
        setAdapter()
        setChipGroup()
        setSearchView()

    }

    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(true)
    }

    private fun setAdapter() {

        //MainProducts
        productAdapter = MainProductAdapter{
            findNavController().navigate(R.id.action_mainFragment_to_detailedFragment,it)
        }
        mainViewModel.products.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                this.productList = it
                productAdapter.submitMainList(productList)
                binding.speacialForMeRcycle.adapter = productAdapter
            }
        }
            //FLashProducts
        flashAdapter = MainProductAdapter{
            findNavController().navigate(R.id.action_mainFragment_to_detailedFragment,it)
        }
        mainViewModel.flashProducts.observe(viewLifecycleOwner) { productList2 ->
                productList2.let {
                    this.flashProductList = it
                    flashAdapter.submitFlashList(productList2)
                    binding.flashProductRcycle.adapter = flashAdapter
                }
            }
                //advertisement Image
                advertisementAdapter = MainAdvertisementAdapter()
                binding.offer.adapter = advertisementAdapter
                mainViewModel.advertList.observe(viewLifecycleOwner) { advertList ->
                    advertList?.let {
                        this.advertList = it
                        advertisementAdapter.submitList(advertList)
                    }
                }
            }

    private fun setChipGroup() {
        firestore.collection("categories").get()
            .addOnSuccessListener {
                for (document in it) {
                    val category = document.data["name"].toString()
                    addChipGroup(category)
                }
            }.addOnFailureListener {
                Log.w(ContentValues.TAG, "Error getting documents.", it)
            }

    }

    private fun addChipGroup(category: String) {
        val chip = Chip(requireContext())
        chip.text = category
        chip.isCheckable = true
        chip.setOnCheckedChangeListener { _, isChecked ->
            filterProductsByCategory(category, isChecked, "MainModel")
            filterProductsByCategory(category, isChecked, "FlashProductModel")
        }
        binding.chipGroup.addView(chip)
    }


    private fun filterProductsByCategory(category: String, isChecked: Boolean, modelType: String) {
        val filteredList = when (modelType) {
            "MainModel" -> {
                if (isChecked) {
                    productList.filter { it.category == category }
                } else {
                    productList
                }
            }

            "FlashProductModel" -> {
                if (isChecked) {
                    flashProductList.filter { it.category == category }
                } else {
                    flashProductList
                }
            }

            else -> {

                emptyList()
            }
        }
        handleFilteredList(filteredList)
    }


    private fun handleFilteredList(filteredList: List<Any>) {
        if (filteredList.isEmpty()) {
        //    Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
        } else {
            when (filteredList.firstOrNull()) {
                is MainModel -> {
                    val mainFilteredList = filteredList as List<MainModel>
                    productAdapter.submitMainList(mainFilteredList)
                }

                is FlashProductModel -> {
                    val flashFilteredList = filteredList as List<FlashProductModel>
                    flashAdapter.submitFlashList(flashFilteredList)
                }

                else -> {

                }
            }
        }
    }

    private fun setSearchView() {
        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    filterProductsByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun filterProductsByName(query: String) {
        val trimmedQuery = query.trim().lowercase(Locale.getDefault())

        // Main Products için filtreleme
        val filteredMainList = productList.filter { product ->
            product.productName?.trim()?.lowercase(Locale.getDefault())?.contains(trimmedQuery) ?: false
        }
        Log.e("MYTA G", "Filtered Main List Size: ${filteredMainList.size}")
        productAdapter.submitMainList(filteredMainList)

        // Flash Products için filtreleme
        val filteredFlashList = flashProductList.filter { product ->
            product.productName?.trim()?.lowercase(Locale.getDefault())?.contains(trimmedQuery) ?: false
        }
        Log.e("MYTA G", "Filtered Flash List Size: ${filteredFlashList.size}")
        flashAdapter.submitFlashList(filteredFlashList)
    }


    override fun onFavoriteChanged(productId: String, isFavorite: Boolean) {
        if (isFavorite) {
            R.drawable.baseline_favorite_border_24
        } else {
            R.drawable.love_svgrepo_com
        }
    }

    override fun onTotalAmountChanged(totalAmount: Double) {

    }
}





