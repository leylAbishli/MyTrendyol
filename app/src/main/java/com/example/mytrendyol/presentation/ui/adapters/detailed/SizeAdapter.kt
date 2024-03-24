package com.example.mytrendyol.presentation.ui.adapters.detailed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.databinding.SizeItemBinding


class SizeAdapter : RecyclerView.Adapter<SizeAdapter.MyViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    private val differCallback = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((Any) -> Unit)? = null

    fun setOnItemClickListener(listener: (Any) -> Unit) {
        onItemClickListener = listener
    }

    inner class MyViewHolder(private val binding: SizeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectItem(position)
                    onItemClickListener?.invoke(differ.currentList[position])
                }
            }
        }

        fun bindForProduct(size: Any, position: Int) {
            if (size is String) {
                binding.textView4.text = size
            } else if (size is Int) {
                binding.textView4.text = size.toString()
            }

            binding.image.visibility = if (position == selectedPosition) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bindForProduct(size, position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitSizeListForProduct(sizes: List<Any>) {
        differ.submitList(sizes)
    }

    private fun selectItem(position: Int) {
        if (selectedPosition != position) {
            val previouslySelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previouslySelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }
}