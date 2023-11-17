package com.example.mycyptos.data.repository

import androidx.paging.PagingSource
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.CryptoPagingSource
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

class CryptoServiceRepositoryImplementation @Inject constructor(private val apiService: CryptoApiService) : CryptoServiceRepository {
    override suspend fun getCryptoData(): PagingSource<Int, Data> {
        return CryptoPagingSource(apiService)
    }

    override suspend fun getTopRankedCryptoData(dataCallback: DataCallback): Data? {
        return CryptoPagingSource(apiService).getTopRankedCrypto(dataCallback)
    }

}