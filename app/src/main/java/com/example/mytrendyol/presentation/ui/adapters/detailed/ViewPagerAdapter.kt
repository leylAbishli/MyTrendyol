package com.example.mytrendyol.presentation.ui.adapters.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.databinding.Viewpager2ItemBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ImageViewHolder>() {

    private var imageUrls: ArrayList<String> = ArrayList()

    fun setImageUrls(newImageUrls: ArrayList<String>) {
        val diffCallback = ImageUrlDiffCallback(imageUrls, newImageUrls)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        imageUrls.clear()
        imageUrls.addAll(newImageUrls)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = Viewpager2ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    inner class ImageViewHolder(private val binding: Viewpager2ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.imageView)
        }
    }

    class ImageUrlDiffCallback(private val oldList: List<String>, private val newList: List<String>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}