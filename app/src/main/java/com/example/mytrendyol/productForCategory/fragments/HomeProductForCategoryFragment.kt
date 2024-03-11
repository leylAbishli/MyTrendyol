package com.example.mytrendyol.productForCategory.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentHomeProductForCategoryBinding
import com.example.mytrendyol.productForCategory.ProductForCategoryViewModel
import com.example.mytrendyol.productForCategory.ProductsAdapter
import com.example.mytrendyol.productForCategory.models.HomeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeProductForCategoryFragment : Fragment() {
    private lateinit var binding: FragmentHomeProductForCategoryBinding
    private lateinit var adapter: ProductsAdapter
    private var productList = listOf<HomeModel>()
    val homeViewModel: ProductForCategoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeProductForCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getHomeProduct(requireContext())
        bottomNavigation()
        setAdapter()

    }


    private fun setAdapter() {
        adapter = ProductsAdapter()
        homeViewModel.homeProducts.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                this.productList = it
                adapter.submitHomeList(productList)
                binding.recycleHome.adapter = adapter
            }
        }

    }
    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(true)
    }


}