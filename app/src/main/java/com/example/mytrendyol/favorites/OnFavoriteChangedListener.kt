package com.example.mytrendyol.favorites

interface OnFavoriteChangedListener {
    fun onFavoriteChanged(productId: String, isFavorite: Boolean)
}