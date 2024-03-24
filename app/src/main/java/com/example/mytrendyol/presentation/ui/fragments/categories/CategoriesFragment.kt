package com.example.mytrendyol.presentation.ui.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mytrendyol.ui.adapters.categories.CategoryAdapter
import com.example.mytrendyol.presentation.ui.models.categories.CategoryModel
import com.example.mytrendyol.presentation.ui.viewmodels.categories.CategoryViewModel
import com.example.mytrendyol.databinding.FragmentCategoriesBinding
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


}