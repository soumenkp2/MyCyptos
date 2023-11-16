package com.example.mycyptos.data.repository

import androidx.paging.PagingSource
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.CryptoPagingSource

class CryptoServiceRepositoryImplementation(private val apiService: CryptoApiService) : CryptoServiceRepository {
    override suspend fun getCryptoData(): PagingSource<Int, Data> {
        return CryptoPagingSource(apiService)
    }

}