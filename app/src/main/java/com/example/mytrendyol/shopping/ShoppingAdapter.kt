//package com.example.mytrendyol.shopping
//
//import android.os.Bundle
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.mytrendyol.databinding.ShoppingItemBinding
//import com.example.mytrendyol.favorites.FavoriteProductAdapter
//import com.example.mytrendyol.main.adapters.MainProductAdapter
//import com.example.mytrendyol.main.models.FlashProductModel
//import com.example.mytrendyol.main.models.MainModel
//import com.google.firebase.firestore.FieldValue
//import com.google.firebase.firestore.SetOptions
//
//class ShoppingAdapter:RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ShoppingAdapter.MyViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: ShoppingAdapter.MyViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    inner class MyViewHolder(private val binding:ShoppingItemBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(product: MainProductAdapter.Product) {
//            when (product) {
//                is MainProductAdapter.Product.MainProduct -> bindMainProduct(product.mainProduct)
//                is MainProductAdapter.Product.FlashProduct -> bindFlashProduct(product.flashProduct)
//                else -> {}
//            }
//        }
//
//        private fun bindMainProduct(current: MainModel) {
//            binding.txtProductName.text = current.productName
//            binding.txtProductPrice.text = current.price.toString()
//            binding.txtDscption.text = current.description
//            Glide.with(binding.root)
//                .load(current.imageUrl)
//                .into(binding.imgProduct)
//            isLiked(current.id.toString(),binding.imgLike)
//            binding.imgLike.setOnClickListener {
//                if (binding.imgLike.tag == "like") {
//                    val hmap = hashMapOf(
//                        current.id to true
//                    )
//                    firestore.collection("likes").document(firebaseUser!!.uid)
//                        .set(hmap, SetOptions.merge())
//
//                } else {
//                    firestore.collection("likes").document(firebaseUser!!.uid)
//                        .update(current.id.toString(), FieldValue.delete())
//                }
//                notifyItemChanged(layoutPosition)
//            }
//        }
//
//
//        private fun bindFlashProduct(current: FlashProductModel) {
//            binding.txtProductName.text = current.productName
//            binding.txtProductPrice.text = current.price.toString()
//            binding.txtDscption.text = current.description
//            Glide.with(binding.root)
//                .load(current.imageUrl)
//                .into(binding.imgProduct)
//            isLiked(current.id.toString(),binding.imgLike)
//            binding.imgLike.setOnClickListener {
//                if (binding.imgLike.tag == "like") {
//                    val hmap = hashMapOf(
//                        current.id to true
//                    )
//                    firestore.collection("likes").document(firebaseUser!!.uid)
//                        .set(hmap, SetOptions.merge())
//
//                } else {
//                    firestore.collection("likes").document(firebaseUser!!.uid)
//                        .update(current.id.toString(), FieldValue.delete())
//
//                }
//                notifyItemChanged(layoutPosition)
//            }
//        }
//    }
//
//    private val diffCallBack = object : DiffUtil.ItemCallback<FavoriteProductAdapter.Product>() {
//        override fun areItemsTheSame(oldItem: FavoriteProductAdapter.Product, newItem: FavoriteProductAdapter.Product): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: FavoriteProductAdapter.Product, newItem: FavoriteProductAdapter.Product): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    private val differ = AsyncListDiffer(this, diffCallBack)
//}