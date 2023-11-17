package com.example.mycyptos.data.repository

import androidx.paging.PagingSource
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

interface CryptoServiceRepository {
    suspend fun getCryptoData(): PagingSource<Int, Data>

    suspend fun getTopRankedCryptoData(dataCallback: DataCallback) : Data?
}