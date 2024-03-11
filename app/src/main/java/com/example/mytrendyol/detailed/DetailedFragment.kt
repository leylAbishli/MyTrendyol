package com.example.mytrendyol.detailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mytrendyol.databinding.FragmentDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    private val viewPagerAdapter by lazy { ViewPagerAdapter() }
    private val sizeAdapter by lazy { SizeAdapter() }
    private val detailViewModel: DetailedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getProduct(requireContext())
        detailViewModel.getFlashProduct(requireContext())
        val id=arguments?.getString("id")


        detailViewModel.products.observe(viewLifecycleOwner)  { productList ->
            val product=productList.find {
                it.id==id
            }
            product?.let { product ->
                binding.productname.text = product.productName
                binding.productDes.text = product.description
                binding.txtPrice.text = product.price.toString()
                viewPagerAdapter.submitImageList(product.imageUrl)
            }
          //  sizeAdapter.submitSizeListForProduct(productList)

        }
        detailViewModel.flashProducts.observe(viewLifecycleOwner) { productList2 ->
            val product=productList2.find {
                it.id==id
            }
            product?.let { product ->
                binding.productname.text = product.productName
                binding.productDes.text = product.description
                binding.txtPrice.text = product.price.toString()
                viewPagerAdapter.submitImageList(product.imageUrl)
            }
           // sizeAdapter.submitSizeListForProduct(productList2)

        }
        setViewPagerAdapter()
        setSizeAdapter()

    }


    private fun setViewPagerAdapter(){
         binding.introViewPager.adapter=viewPagerAdapter

    }

    private fun setSizeAdapter(){

        binding.sizercycle.adapter=sizeAdapter
    }
}