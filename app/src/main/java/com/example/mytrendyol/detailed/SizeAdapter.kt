package com.example.mytrendyol.detailed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.databinding.SizeItemBinding
import com.example.mytrendyol.main.models.FlashProductModel
import com.example.mytrendyol.main.models.MainModel
import java.io.Serializable

class SizeAdapter : RecyclerView.Adapter<SizeAdapter.MyViewHolder>() {
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.MyViewHolder {
        return MyViewHolder(
            SizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SizeAdapter.MyViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(size)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(val binding: SizeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindForProduct(size: MainModel, position: Int) {
            binding.textView4.text = size.size.toString()
            if (position == selectedPosition) {
                binding.apply {
                    image.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    image.visibility = View.INVISIBLE
                }

            }
        }

        fun bindForFlashProduct(size: FlashProductModel, position: Int) {
            binding.textView4.text = size.size.toString()
            if (position == selectedPosition) {
                binding.apply {
                    image.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    image.visibility = View.INVISIBLE
                }

            }
        }

        fun bind(product: Product, position: Int) {
            when (product) {
                is Product.MainProduct -> bindForProduct(
                    product.mainProduct,
                    bindingAdapterPosition
                )

                is Product.FlashProduct -> bindForFlashProduct(
                    product.flashProduct,
                    bindingAdapterPosition
                )

                else -> {}
            }
        }


    }

    fun submitSizeListForProduct(sizes: List<MainModel>) {
        differ.submitList(sizes.map { Product.MainProduct(it) })
    }

    fun submitSizeListForFlashProduct(sizes: List<FlashProductModel>) {
        differ.submitList(sizes.map { Product.FlashProduct(it) })
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallBack)
    var onItemClick: ((Product) -> Unit)? = null

    sealed class Product : Serializable {
        data class MainProduct(val mainProduct: MainModel) : Product()
        data class FlashProduct(val flashProduct: FlashProductModel) : Product()
    }
}

