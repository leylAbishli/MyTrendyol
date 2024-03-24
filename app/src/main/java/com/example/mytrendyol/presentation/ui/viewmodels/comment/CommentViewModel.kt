package com.example.mytrendyol.presentation.ui.viewmodels.comment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytrendyol.utils.ConstValues
import com.example.mytrendyol.presentation.ui.models.comment.CommentModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel  @Inject constructor(private val db: FirebaseFirestore): ViewModel() {

    private val _comments = MutableLiveData<List<CommentModel>>()
    val comments: LiveData<List<CommentModel>> get() = _comments

    fun getComments(context: Context,productId:String?) {
        db.collection("comments")
            .whereEqualTo("id",productId)
            .get()
            .addOnSuccessListener { result ->
                val commentList = mutableListOf<CommentModel>()
                for (document in result) {
                    val info = CommentModel(
                        comment = document.getString(ConstValues.COMMENT_FIELD) ?: "",
                        username = document.getString(ConstValues.USERNAME_FIELD) ?: "",
                        time = document.get(ConstValues.TIME_FIELD) ?: ""
                    )
                    commentList.add(info)
                }
                _comments.value = commentList
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Error getting products: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()// Toast.makeText()
            }

    }
}