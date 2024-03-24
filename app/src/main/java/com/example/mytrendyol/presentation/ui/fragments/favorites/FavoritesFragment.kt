package com.example.mytrendyol.presentation.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.databinding.FragmentFavoritesBinding
import com.example.mytrendyol.ui.adapters.detailed.SizeAdapter
import com.example.mytrendyol.ui.adapters.favorites.FavoriteProductAdapter
import com.example.mytrendyol.presentation.ui.viewmodels.favorites.FavoritesViewModel
import com.example.mytrendyol.presentation.ui.adapters.favorites.OnChangedListener
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), OnChangedListener {
    private var favList1 = ArrayList<MainModel>()
    private var favList2 = listOf<FlashProductModel>()
    private lateinit var binding:FragmentFavoritesBinding
     lateinit var firestore: FirebaseFirestore
     lateinit var adapter: FavoriteProductAdapter
    private val sizeAdapter: SizeAdapter by lazy { SizeAdapter() }
   private   val favViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favViewModel.mySaves()
        bottomNavigation()
        setAdapter()
    }
    private fun setAdapter(){
        adapter= FavoriteProductAdapter(this)
        favViewModel.favoriteProducts.observe(viewLifecycleOwner){favoriteProducts->
            favoriteProducts?.let {
                this.favList1=it
                adapter.submitMainList(it)

            }
            binding.rcycleFav.adapter=adapter

        }
        favViewModel.favoriteProducts2.observe(viewLifecycleOwner){favoriteProducts2->
            favoriteProducts2?.let {
                favList1.addAll(it)
                adapter.submitMainList(favList1)
            }
            binding.rcycleFav.adapter=adapter

        }
    }
    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(true)
    }

    override fun onFavoriteChanged(productId: String, isFavorite: Boolean) {
        favViewModel.mySaves()
    }

    override fun onTotalAmountChanged(totalAmount: Double) {
        //
    }

}