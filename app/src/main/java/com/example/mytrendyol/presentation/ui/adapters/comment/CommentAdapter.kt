package com.example.mytrendyol.presentation.ui.adapters.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrendyol.presentation.ui.models.comment.CommentModel
import com.example.mytrendyol.databinding.CommentItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class MyViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: CommentModel) {
            binding.commentTxt.text=current.comment
            binding.publisherTxt.text=current.username
//            val timestamp = current.time
//            val date = timestamp.
//            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
//            binding.timeTxt.text = dateFormat.format(date)
        }


    }


    private val diffCallBack = object : DiffUtil.ItemCallback<CommentModel>() {
        override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(info: List<CommentModel>) {
        differ.submitList(info)
    }
}