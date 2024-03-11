package com.example.mytrendyol.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FavorituesProductItemBinding
import com.example.mytrendyol.main.adapters.MainProductAdapter
import com.example.mytrendyol.main.models.FlashProductModel
import com.example.mytrendyol.main.models.MainModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore



class FavoriteProductAdapter(private val listener: OnFavoriteChangedListener) :
    RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {
    private val firebaseUser = Firebase.auth.currentUser
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteProductAdapter.ViewHolder {
        val view =
            FavorituesProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        firestore = FirebaseFirestore.getInstance()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteProductAdapter.ViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this, diffCallBack)

    inner class ViewHolder(private val binding: FavorituesProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            when (product) {
                is Product.MainProduct -> bindMainProduct(product.mainProduct)
                is Product.FlashProduct -> bindFlashProduct(product.flashProduct)
                else -> {}
            }
        }

        private fun bindMainProduct(current: MainModel) {
            binding.favName.text = current.productName
            binding.favPrice.text = current.price.toString()
            binding.favDescription.text = current.description
            Glide.with(binding.root)
                .load(current.imageUrl?.get(0))
                .into(binding.imageViewFav)
            binding.imageViewTrash.setOnClickListener {
                removeFromFavorites(current.id.toString())
                notifyItemChanged(layoutPosition)
            }

        }


        private fun bindFlashProduct(current: FlashProductModel) {
            binding.favName.text = current.productName
            binding.favPrice.text = current.price.toString()
            binding.favDescription.text = current.description
            Glide.with(binding.root)
                .load(current.imageUrl)
                .into(binding.imageViewFav)
            binding.imageViewTrash.setOnClickListener {
                    removeFromFavorites(current.id.toString())
                    notifyItemChanged(layoutPosition)
                }

            }
        }

        private fun removeFromFavorites(productId: String) {
            val favoritesRef = firestore.collection("likes").document(firebaseUser!!.uid)
            favoritesRef.update(productId, FieldValue.delete())
                .addOnSuccessListener {
                    listener.onFavoriteChanged(productId, false)
                    Log.d("FavoriteProductAdapter", "Product removed from favorites.")
//                    val currentPosition = differ.currentList.indexOfFirst {
//                        when (it) {
//                            is Product.MainProduct -> it.mainProduct.id.toString() == productId
//                            is Product.FlashProduct -> it.flashProduct.id.toString() == productId
//                            else -> false
//                        }
//                    }
//                    if (currentPosition != -1) {
//                        val newList = differ.currentList.toMutableList()
//                        notifyItemRemoved(currentPosition)
//                        differ.submitList(newList)
//
//                    }
                }

                .addOnFailureListener { e ->
                    Log.e("FavoriteProductAdapter", "Error removing product from favorites", e)
                }
        }



    fun submitMainList(products: List<MainModel>) {
        differ.submitList(products.map { Product.MainProduct(it) })
    }

    fun submitFlashList(products: List<FlashProductModel>) {
        differ.submitList(products.map { Product.FlashProduct(it) })

    }

    sealed class Product {
        data class MainProduct(val mainProduct: MainModel) : Product()
        data class FlashProduct(val flashProduct: FlashProductModel) : Product()
    }

}



