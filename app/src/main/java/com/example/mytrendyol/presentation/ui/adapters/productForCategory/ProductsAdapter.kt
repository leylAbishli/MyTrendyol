package com.example.mytrendyol.presentation.ui.adapters.productForCategory

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.MainProductsItemBinding
import com.example.mytrendyol.presentation.ui.models.productForCategory.HomeModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {
    private val firebaseUser = Firebase.auth.currentUser
    private lateinit var firestore: FirebaseFirestore


    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MainProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        firestore = FirebaseFirestore.getInstance()
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitHomeList(products: List<HomeModel>) {
        val productList = products.map { Product.HomeProduct(it) }
        differ.submitList(productList)
    }


    inner class MyViewHolder(private val binding: MainProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            when (product) {
                is Product.HomeProduct -> bindHomeProduct(product.homeProduct)
                else -> {}
            }
        }

        private fun bindHomeProduct(current: HomeModel) {
            binding.txtProductName.text = current.name
            binding.txtProductPrice.text = current.price.toString()
            binding.txtDscption.text = current.description
            Glide.with(binding.root)
                .load(current.imageUrl)
                .into(binding.imgProduct)
            isLiked(current.id.toString(), binding.imgLike)
            binding.imgLike.setOnClickListener {
                if (binding.imgLike.tag == "like") {
                    val hmap = hashMapOf(
                        current.id to true
                    )
                    firestore.collection("likes").document(firebaseUser!!.uid)
                        .set(hmap, SetOptions.merge())

                } else {
                    firestore.collection("likes").document(firebaseUser!!.uid)
                        .update(current.id.toString(), FieldValue.delete())
                }
                notifyItemChanged(layoutPosition)
            }
        }




        private fun isLiked(id: String, imageView: ImageView) {
            val ref = firestore.collection("likes").document(firebaseUser!!.uid)
            ref.addSnapshotListener { value, error ->
                if (error != null) {
                    error.localizedMessage?.let { Log.e("error", it) }
                } else {
                    if (value != null) {
                        if (value.contains(id)) {
                            imageView.setImageResource(R.drawable.love_svgrepo_com)
                            imageView.tag = "liked"
                        } else {
                            imageView.setImageResource(R.drawable.baseline_favorite_border_24)
                            imageView.tag = "like"
                        }
                    }

                }
            }
        }

    }


    sealed class Product {
        data class HomeProduct(val homeProduct: HomeModel) : Product()

    }
}