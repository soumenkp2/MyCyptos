package com.example.mycyptos.domain

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.paging.DataCallback
import javax.inject.Inject

/**
 * Use case for handling top-ranked cryptocurrency data.
 * @param cryptoServiceRepository: Repository for accessing cryptocurrency data from a service.
 * @param context: The application context.
 */
class TopCryptoUsecase @Inject constructor(private val cryptoServiceRepository: CryptoServiceRepository,private val context: Context) {

    /**
     * Invokes the use case to fetch top-ranked cryptocurrency data for pagination.
     * @return PagingSource<Int, Data>: Returns a PagingSource for paginating through top-ranked cryptocurrency data.
     */
    suspend operator fun invoke(): PagingSource<Int,Data> {
        val cryptoDataList = cryptoServiceRepository.getCryptoData(context)
        return try {
            cryptoDataList
        } catch (e: Exception) {
            e.printStackTrace()
            cryptoDataList
        }
    }

    /**
     * Retrieves a list of all cryptocurrencies.
     * @return List<Data>: Returns a list of Data objects representing cryptocurrencies.
     */
    suspend fun getCryptoListData() : List<Data> {
        return cryptoServiceRepository.getCryptoList()
    }

    /**
     * Retrieves the top-ranked cryptocurrency data.
     * @param dataCallback: Callback for handling data response.
     * @return Data?: Returns a single Data object representing the top-ranked cryptocurrency, or null if not available.
     */
    suspend fun getTopRankedCryptoData(dataCallback: DataCallback) : Data? {
        return cryptoServiceRepository.getTopRankedCryptoData(dataCallback,context)
    }
}