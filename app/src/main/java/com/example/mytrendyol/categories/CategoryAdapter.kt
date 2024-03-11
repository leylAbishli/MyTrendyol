package com.example.mytrendyol.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.databinding.CategoriesItemBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view =
            CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

    }

    private val diffUtil = AsyncListDiffer(this, diffCallBack)


    inner class ViewHolder(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(current: CategoryModel) {

            binding.txtCategory.text = current.categoryName
            Glide.with(binding.root)
                .load(current.imageUrl)
                .into(binding.imgCategory)

        }
    }

     fun submitList(catLists: List<CategoryModel>) {
        diffUtil.submitList(catLists)
    }
}