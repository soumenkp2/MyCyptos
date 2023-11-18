package com.example.mycyptos.domain

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

class FavCryptoUsecase @Inject constructor(private val cryptoServiceRepository: CryptoServiceRepository, private val context: Context) {

    suspend operator fun invoke(): PagingSource<Int, Data> {
        val cryptoDataList = cryptoServiceRepository.getFavCryptoData(context)
        return try {
            cryptoDataList
        } catch (e: Exception) {
            e.printStackTrace()
            cryptoDataList
        }
    }
}