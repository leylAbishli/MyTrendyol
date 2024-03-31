package com.example.mytrendyol.presentation.ui.viewmodels.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrendyol.domain.repositoryy.main.MainRepositoryImp
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel
import com.example.mytrendyol.presentation.ui.models.main.CategoryModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(  private val mainRepository: MainRepositoryImp) : ViewModel() {

    private val _imageResource = MutableLiveData<Int>()
    val imageResource: LiveData<Int> = _imageResource

    private val _products = MutableLiveData<List<MainModel>>()
    val products: LiveData<List<MainModel>> get() = _products

    private val _flashProducts = MutableLiveData<List<FlashProductModel>>()
    val flashProducts: LiveData<List<FlashProductModel>> get() = _flashProducts

    private val _advertList = MutableLiveData<List<AdvertisementModel>>()
    val advertList: LiveData<List<AdvertisementModel>> get() = _advertList

    private val _likedProducts = MutableLiveData<Set<String>>()
    val likedProducts: LiveData<Set<String>> = _likedProducts




    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> get() = _categories


    fun getProduct(context: Context) {
        viewModelScope.launch {
            _products.value = mainRepository.getProducts()
        }
    }

    fun getFlashProduct(context: Context) {
        viewModelScope.launch {
            _flashProducts.value = mainRepository.getFlashProducts()
        }
    }

    fun getAdvertisementImage(context: Context) {

        viewModelScope.launch {
            _advertList.value = mainRepository.getAdvertisementImages()
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = mainRepository.getCategories()
        }
    }




}


