package com.example.mycyptos.data.repository

import android.content.Context
import androidx.paging.PagingSource
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

interface CryptoServiceRepository {
    /**
     * Suspended function to fetch cryptocurrency data for pagination.
     * @param context: The application context.
     * @return PagingSource<Int, Data>: Returns a PagingSource for paginating through cryptocurrency data.
     */
    suspend fun getCryptoData(context: Context): PagingSource<Int, Data>

    /**
     * Suspended function to retrieve the top-ranked cryptocurrency data.
     * @param dataCallback: Callback for handling data response.
     * @param context: The application context.
     * @return Data?: Returns a single Data object representing the top-ranked cryptocurrency, or null if not available.
     */
    suspend fun getTopRankedCryptoData(dataCallback: DataCallback,context: Context) : Data?

    /**
     * Suspended function to fetch favorite cryptocurrency data for pagination.
     * @param context: The application context.
     * @return PagingSource<Int, Data>: Returns a PagingSource for paginating through favorite cryptocurrency data.
     */
    suspend fun getFavCryptoData(context: Context) : PagingSource<Int, Data>

    /**
     * Suspended function to get a list of all cryptocurrencies.
     * @return List<Data>: Returns a list of Data objects representing cryptocurrencies.
     */
    suspend fun getCryptoList(): List<Data>
}