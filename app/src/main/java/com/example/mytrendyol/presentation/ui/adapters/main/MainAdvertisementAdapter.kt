package com.example.mytrendyol.presentation.ui.adapters.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrendyol.databinding.MainProductsItem2Binding
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel

class MainAdvertisementAdapter:RecyclerView.Adapter<MainAdvertisementAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            MainProductsItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     return holder.bind(diffUtil.currentList[position])
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<AdvertisementModel>() {
        override fun areItemsTheSame(oldItem: AdvertisementModel, newItem: AdvertisementModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AdvertisementModel, newItem: AdvertisementModel): Boolean {
            return oldItem == newItem
        }

    }

    private val diffUtil = AsyncListDiffer(this, diffCallBack)

    inner class MyViewHolder(private val binding:MainProductsItem2Binding):RecyclerView.ViewHolder(binding.root){

        fun bind(current: AdvertisementModel){
            Glide.with(binding.root)
                .load(current.imageUrl)
                .into(binding.imageViewAdvertism)
        }
    }
    fun submitList(imgLists: List<AdvertisementModel>) {
        diffUtil.submitList(imgLists)
    }
}