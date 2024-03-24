package com.example.mytrendyol.presentation.ui.fragments.detailed


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mytrendyol.ui.activity.MainActivity
import com.example.mytrendyol.ui.adapters.comment.CommentAdapter
import com.example.mytrendyol.presentation.ui.models.comment.CommentModel
import com.example.mytrendyol.presentation.ui.viewmodels.comment.CommentViewModel
import com.example.mytrendyol.databinding.FragmentDetailedBinding
import com.example.mytrendyol.presentation.ui.viewmodels.detailed.DetailedViewModel
import com.example.mytrendyol.ui.adapters.detailed.SizeAdapter
import com.example.mytrendyol.ui.adapters.detailed.ViewPagerAdapter
import com.example.mytrendyol.presentation.ui.models.detailed.ProductInfoModel
import com.example.mytrendyol.presentation.ui.viewmodels.detailed.productInfo.ProductInfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    private var list = listOf<CommentModel>()
    private var listProductInfo = listOf<ProductInfoModel>()
    private val viewPagerAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter() }
    private val sizeAdapter: SizeAdapter by lazy { SizeAdapter() }
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var firestore: FirebaseFirestore
    private val detailViewModel: DetailedViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private val productInfoViewModel: ProductInfoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater)
        firestore = FirebaseFirestore.getInstance()
        commentAdapter = CommentAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getProduct(requireContext())
        detailViewModel.getFlashProduct(requireContext())
        //  detailViewModel.fetchDataFromFirebase()
        val id = arguments?.getString("id")
        commentViewModel.getComments(requireContext(), id)
        productInfoViewModel.getProductInfo(requireContext(), id)
        detailViewModel.products.observe(viewLifecycleOwner) { productList ->
            val product = productList.find {
                it.id == id
            }
            product?.let { product ->
                binding.productname.text = product.productName
                binding.productDes.text = product.description
                binding.txtPrice.text = product.price.toString()
                val sizes = product.size ?: emptyList()
                if (sizes.isEmpty()) {
                    binding.cardview.visibility = View.GONE
                } else {
                    sizeAdapter.submitSizeListForProduct(sizes)
                    binding.sizercycle.adapter = sizeAdapter
                    Log.d("aaaa", "$sizes")
                }
                product.imageUrl?.let {
                    viewPagerAdapter.setImageUrls(it as ArrayList<String>)
                    binding.introViewPager.adapter = viewPagerAdapter
                }
            }
        }

        detailViewModel.flashProducts.observe(viewLifecycleOwner) { productList2 ->
            val product = productList2.find {
                it.id == id
            }
            product?.let { product ->
                binding.productname.text = product.productName
                binding.productDes.text = product.description
                binding.txtPrice.text = product.price.toString()
                val sizes = product.size ?: emptyList() // Firebase'den gelen boyut listesi
                if (sizes.isEmpty()) {
                    binding.cardview.visibility = View.GONE
                } else {
                    sizeAdapter.submitSizeListForProduct(sizes)
                    binding.sizercycle.adapter = sizeAdapter
                    Log.d("aaaa", "$sizes")
                }
            }
            product?.imageUrl?.let {
                viewPagerAdapter.setImageUrls(it as ArrayList<String>)
                binding.introViewPager.adapter = viewPagerAdapter
                Log.e(TAG, "onViewCreated: ",)
            }
        }


        setCommentAdapter()
        bottomNavigation()
        setProductInfo()
    }

    private fun setCommentAdapter(){
        commentViewModel.comments.observe(viewLifecycleOwner){ list->
            list?.let {
                this.list = it
                if (list.isEmpty()) {
                    binding.commentCardview.visibility=View.GONE
                } else {
                    commentAdapter.submitList(list)
                    binding.recyclerView2.adapter = commentAdapter
                }
            }
        }
    }
    private fun setProductInfo() {
        productInfoViewModel.productInfo.observe(viewLifecycleOwner) { listProductInfo ->
            listProductInfo?.let {
                this.listProductInfo = it
                val productInfo = it.firstOrNull()
                productInfo?.let { info ->
                    binding.txtColor.text = info.color
                    binding.txtSize.text=info.size
                    binding.txtSealer.text=info.sealer
                    binding.txtMaterial.text=info.material
                }
            }
        }
    }


    private fun bottomNavigation() {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigation(false)
    }
}