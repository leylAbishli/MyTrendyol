package com.example.mytrendyol.domain.usecasee

import com.example.mytrendyol.domain.repositoryy.main.MainRepositoryImp
import com.example.mytrendyol.presentation.ui.models.main.AdvertisementModel
import com.example.mytrendyol.presentation.ui.models.main.CategoryModel
import com.example.mytrendyol.presentation.ui.models.main.FlashProductModel
import com.example.mytrendyol.presentation.ui.models.main.MainModel
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepositoryImp) {

    suspend  fun getProducts(): List<MainModel> {
        return mainRepository.getProducts()
    }

    suspend operator fun invoke(): List<FlashProductModel> {
        return mainRepository.getFlashProducts()
    }

    suspend fun getAdvertisementImages(): List<AdvertisementModel> {
        return mainRepository.getAdvertisementImages()
    }

    suspend fun getCategories(): List<CategoryModel> {
        return mainRepository.getCategories()
    }
}