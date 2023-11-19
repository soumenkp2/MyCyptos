package com.example.mycyptos.domain

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

/**
 * Use case for retrieving favorite cryptocurrency data.
 * @param cryptoServiceRepository: Repository for accessing cryptocurrency data from a service.
 * @param context: The application context.
 */
class FavCryptoUsecase @Inject constructor(private val cryptoServiceRepository: CryptoServiceRepository, private val context: Context) {

    /**
     * Invokes the use case to fetch favorite cryptocurrency data for pagination.
     * @return PagingSource<Int, Data>: Returns a PagingSource for paginating through favorite cryptocurrency data.
     */
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