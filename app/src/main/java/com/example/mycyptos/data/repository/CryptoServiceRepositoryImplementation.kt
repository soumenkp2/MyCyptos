package com.example.mycyptos.data.repository

import android.content.Context
import androidx.paging.PagingSource
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.CryptoPagingSource
import com.example.mycyptos.paging.DataCallback
import com.example.mycyptos.utils.AppConstants
import javax.inject.Inject

class CryptoServiceRepositoryImplementation @Inject constructor(private val apiService: CryptoApiService) : CryptoServiceRepository {
    override suspend fun getCryptoData(context: Context): PagingSource<Int, Data> {
        return CryptoPagingSource(apiService,AppConstants.all_crypto_list_key,context)
    }

    override suspend fun getTopRankedCryptoData(dataCallback: DataCallback, context: Context): Data? {
        return CryptoPagingSource(apiService,AppConstants.first_crypto_key,context).getTopRankedCrypto(dataCallback)
    }

    override suspend fun getFavCryptoData(context: Context): PagingSource<Int, Data> {
        return CryptoPagingSource(apiService,AppConstants.fav_crypto_list_key,context)
    }

}