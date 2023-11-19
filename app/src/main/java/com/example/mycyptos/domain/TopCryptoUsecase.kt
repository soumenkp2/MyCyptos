package com.example.mycyptos.domain

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

class TopCryptoUsecase @Inject constructor(private val cryptoServiceRepository: CryptoServiceRepository,private val context: Context) {

    suspend operator fun invoke(): PagingSource<Int,Data> {
        val cryptoDataList = cryptoServiceRepository.getCryptoData(context)
        return try {
            cryptoDataList
        } catch (e: Exception) {
            e.printStackTrace()
            cryptoDataList
        }
    }

    suspend fun getCryptoListData() : List<Data> {
        return cryptoServiceRepository.getCryptoList()
    }

    suspend fun getTopRankedCryptoData(dataCallback: DataCallback) : Data? {
        Log.d("api usecase data",cryptoServiceRepository.getTopRankedCryptoData(dataCallback,context).toString())
        return cryptoServiceRepository.getTopRankedCryptoData(dataCallback,context)
    }
}