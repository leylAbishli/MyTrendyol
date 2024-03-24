package com.example.mytrendyol.presentation.ui.adapters.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.databinding.CardinformationItemBinding
import com.example.mytrendyol.presentation.ui.models.shopping.UserShopModel
import com.google.firebase.firestore.FirebaseFirestore

class CardAdapter: RecyclerView.Adapter<CardAdapter.MyViewHolder>() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            CardinformationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class MyViewHolder(private val binding: CardinformationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: UserShopModel) {
            binding.cardTxt.text=current.cartNumber.toString()
        }


    }


    private val diffCallBack = object : DiffUtil.ItemCallback<UserShopModel>() {
        override fun areItemsTheSame(oldItem: UserShopModel, newItem: UserShopModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserShopModel, newItem: UserShopModel): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(info: List<UserShopModel>) {
        differ.submitList(info)
    }
}