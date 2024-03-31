package com.example.mytrendyol.presentation.ui.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.presentation.ui.models.categories.CategoryModel
import com.example.mytrendyol.presentation.ui.viewmodels.categories.CategoryViewModel
import com.example.mytrendyol.databinding.FragmentCategoriesBinding
import com.example.mytrendyol.presentation.ui.adapters.categories.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var categoryList = listOf<CategoryModel>()
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var adapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel.getCategoryForCategories(requireContext())
        setAdapter()
        backView()

    }
    private fun setAdapter(){
        adapter = CategoryAdapter()
        binding.rcycleCate.adapter = adapter
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            categoryList?.let {
                this.categoryList=it
                adapter.submitList(categoryList)
            }
        }
    }

    private fun backView(){
        binding.imageView46.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesFragment_to_mainFragment)
        }
    }

}