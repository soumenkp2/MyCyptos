package com.example.mycyptos.domain

import androidx.paging.PagingSource
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.datamodels.Data
import javax.inject.Inject

class TopCryptoUsecase @Inject constructor(private val cryptoServiceRepository: CryptoServiceRepository) {
    suspend operator fun invoke(): PagingSource<Int,Data> {
        val cryptoDataList = cryptoServiceRepository.getCryptoData()
        return try {
            cryptoDataList
        } catch (e: Exception) {
            e.printStackTrace()
            cryptoDataList
        }
    }
}