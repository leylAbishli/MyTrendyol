package com.example.mytrendyol.presentation.ui.adapters.shopping

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.databinding.ShoppingItemBinding
import com.example.mytrendyol.presentation.ui.adapters.favorites.OnChangedListener
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ShoppingAdapter(private val listener: OnChangedListener) :
    RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>() {
    private val firebaseUser = Firebase.auth.currentUser
    private lateinit var firestore: FirebaseFirestore
    private var totalAmount: Double = 0.0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        firestore = FirebaseFirestore.getInstance()
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(private val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: MainModel) {
            binding.shopName.text = current.productName
            binding.shopDesc.text = current.description
            binding.shopPrice.text = current.price.toString()
            Glide.with(binding.root)
                .load(current.imageUrl?.get(0))
                .into(binding.imageViewShop)
            binding.imgtrash.setOnClickListener {
                removeFromFavorites(current.id.toString())
                notifyItemChanged(layoutPosition)
            }

            binding.btnPlus.setOnClickListener {
                current.quantity++
                binding.txtChangePrice.text = current.quantity.toString()
                binding.shopPrice.text = calculatePrice(current).toString()
                updateTotalAmount(current.price ?: 0.0, 1)
                updateTotalPrice()
            }

            binding.btnMinus.setOnClickListener {
                if (current.quantity > 0) {
                    current.quantity--
                    binding.txtChangePrice.text = current.quantity.toString()
                    binding.shopPrice.text = calculatePrice(current).toString()
                    updateTotalAmount(-1 * (current.price ?: 0.0), -1)
                    updateTotalPrice()
                }
            }
            updateTotalPrice()
        }

        private fun calculatePrice(product: MainModel): Double {

            return product.price?.times(product.quantity) ?: 0.0
        }

        private fun updateTotalAmount(priceChange: Double, quantityChange: Int) {
            totalAmount += priceChange * quantityChange
            listener.onTotalAmountChanged(totalAmount)
        }

        private fun updateTotalPrice() {
            totalAmount = 0.0
            differ.currentList.forEach { product ->
                totalAmount += calculatePrice(product)
            }
            listener.onTotalAmountChanged(totalAmount)
        }

        private fun removeFromFavorites(productId: String) {
            val favoritesRef = firestore.collection("shopping").document(firebaseUser!!.uid)
            favoritesRef.update(productId, FieldValue.delete())
                .addOnSuccessListener {
                    listener.onFavoriteChanged(productId, false)
                    Log.d("FavoriteProductAdapter", "Product removed from favorites.")
                }

                .addOnFailureListener { e ->
                    Log.e("FavoriteProductAdapter", "Error removing product from favorites", e)
                }
        }


    }


    private val diffCallBack = object : DiffUtil.ItemCallback<MainModel>() {
        override fun areItemsTheSame(oldItem: MainModel, newItem: MainModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MainModel, newItem: MainModel): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(products: List<MainModel>) {
        differ.submitList(products)
    }

}