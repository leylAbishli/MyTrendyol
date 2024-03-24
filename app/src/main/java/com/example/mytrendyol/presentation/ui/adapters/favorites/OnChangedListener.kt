package com.example.mytrendyol.presentation.ui.adapters.favorites

interface OnChangedListener {
    fun onFavoriteChanged(productId: String, isFavorite: Boolean)
    fun onTotalAmountChanged(totalAmount: Double)
}