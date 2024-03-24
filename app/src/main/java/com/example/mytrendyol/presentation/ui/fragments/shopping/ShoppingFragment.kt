package com.example.mytrendyol.presentation.ui.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mytrendyol.R
import com.example.mytrendyol.ui.adapters.shopping.ShoppingAdapter
import com.example.mytrendyol.databinding.FragmentShoppingBinding
import com.example.mytrendyol.presentation.ui.adapters.favorites.OnChangedListener
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.example.mytrendyol.presentation.ui.viewmodels.shopping.ShoppingViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : Fragment(), OnChangedListener {
    private var shopList = ArrayList<MainModel>()
    lateinit var firestore: FirebaseFirestore
    lateinit var adapter: ShoppingAdapter
    private   val shopViewModel: ShoppingViewModel by viewModels()
    private lateinit var binding:FragmentShoppingBinding
    private var totalAmount: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel.mySaves()
        setAdapter()
        setCard()
    }


    private fun setAdapter(){
        adapter= ShoppingAdapter(this)
        shopViewModel.products.observe(viewLifecycleOwner){products->
            products?.let {
                this.shopList= it
                adapter.submitList(it)

            }
            binding.rcycleFav.adapter=adapter
        }

    }


    override fun onFavoriteChanged(productId: String, isFavorite: Boolean) {
        shopViewModel.mySaves()
    }

    override fun onTotalAmountChanged(totalAmount: Double) {
        this.totalAmount = totalAmount
        val formattedTotalAmount = String.format("%.2f", totalAmount)
        binding.shoppingChnage.text=formattedTotalAmount
    }
    private fun setCard(){
        binding.buttonConfirm.setOnClickListener{
            val bundle = Bundle()
            bundle.putDouble("totalAmount", totalAmount)
            val fragment = ShoppingCardFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}