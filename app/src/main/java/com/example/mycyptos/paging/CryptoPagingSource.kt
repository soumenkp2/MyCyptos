package com.example.mycyptos.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.CryptoData
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.utils.AppConstants
import javax.inject.Inject

class CryptoPagingSource @Inject constructor(
    val apiService: CryptoApiService
) : PagingSource<Int,Data>() {

    private lateinit var cryptoData: CryptoData
    private lateinit var cryptoDataList: List<Data>
    private var topRankedCryptoData: Data ?= null

    /**
     * Fetches paginated messages from the remote API based on the provided parameters.
     *
     * @param params The parameters for loading pages.
     * @return A LoadResult containing paginated data or an error.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Data> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getCryptoDataList(AppConstants.API_KEY,AppConstants.sort,AppConstants.sort_dir)
            cryptoData = response
            cryptoDataList = cryptoData.data
            Log.d("api response",response.data.toString())
            topRankedCryptoData = evaluateTopRankedCrypto(cryptoDataList)
            Log.d("api response top",topRankedCryptoData.toString())
            LoadResult.Page(
                data = cryptoDataList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (cryptoDataList.isEmpty() || (page*params.loadSize)>=cryptoDataList.size) null else page + 1
            )
        } catch (e: Exception) {
            Log.d("api response error",e.toString())
            LoadResult.Error(e)
        }
    }

    /**
     * Retrieves the refresh key for the current paging state.
     *
     * @param state The current PagingState.
     * @return The refresh key for the state.
     */
    override fun getRefreshKey(state: PagingState<Int,Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    fun evaluateTopRankedCrypto(cryptoDataList: List<Data>) : Data? {
        if(!cryptoDataList.isEmpty()) {
            return cryptoDataList.get(0)
        } else {
            return null
        }
    }

    fun getTopRankedCrypto(dataCallback: DataCallback) : Data? {
        topRankedCryptoData?.let { dataCallback.onDataReceived(it) }
        return topRankedCryptoData
    }


}

interface DataCallback {
    fun onDataReceived(dataLoaded: Data)
}
