package com.example.mytrendyol.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.databinding.Viewpager2ItemBinding

class ViewPagerAdapter:RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
    private val imagesList = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.MyViewHolder {
        return MyViewHolder(
            Viewpager2ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.MyViewHolder, position: Int) {
        val imageUrl = imagesList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    inner class MyViewHolder (val binding:Viewpager2ItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imageUrl:String) {
             Glide.with(binding.root).load(imageUrl).into(binding.imageView)

        }

    }
    fun submitImageList(images: ArrayList<*>?) {
        images?.let {
            val stringUrls = it.filterIsInstance<String>()
            imagesList.clear()
            imagesList.addAll(stringUrls)

        }
    }

    private val diffCallBack=object:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffCallBack)
}