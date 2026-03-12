package com.example.mytrendyol.presentation.ui.adapters.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.databinding.AddressItemBinding
import com.example.mytrendyol.presentation.ui.models.shopping.UserShopModel
import com.google.firebase.firestore.FirebaseFirestore

class UserInfoAdapter: RecyclerView.Adapter<UserInfoAdapter.MyViewHolder>() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class MyViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: UserShopModel) {
            binding.infoName.text=current.name
            binding.phoneNumberTxt.text=current.phoneNumber.toString()
            binding.regionTxt.text=current.region
            binding.countryTxt.text=current.country
            binding.addressTxt.text=current.address
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