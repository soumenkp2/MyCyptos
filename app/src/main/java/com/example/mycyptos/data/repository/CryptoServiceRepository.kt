package com.example.mycyptos.data.repository

import android.content.Context
import androidx.paging.PagingSource
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

interface CryptoServiceRepository {
    suspend fun getCryptoData(context: Context): PagingSource<Int, Data>

    suspend fun getTopRankedCryptoData(dataCallback: DataCallback,context: Context) : Data?

    suspend fun getFavCryptoData(context: Context) : PagingSource<Int, Data>

    suspend fun getCryptoList(): List<Data>
}