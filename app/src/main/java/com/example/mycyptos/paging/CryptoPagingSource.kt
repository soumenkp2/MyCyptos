package com.example.mycyptos.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.CryptoData
import com.example.mycyptos.datamodels.Data
import javax.inject.Inject

class CryptoPagingSource @Inject constructor(
    val apiService: CryptoApiService
) : PagingSource<Int,Data>() {

    private lateinit var cryptoData: CryptoData
    private lateinit var cryptoDataList: List<Data>

    /**
     * Fetches paginated messages from the remote API based on the provided parameters.
     *
     * @param params The parameters for loading pages.
     * @return A LoadResult containing paginated data or an error.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Data> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getCryptoDataList()
            cryptoData = response
            cryptoDataList = cryptoData.data

            LoadResult.Page(
                data = cryptoDataList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (cryptoDataList.isEmpty() || (page*params.loadSize)>=cryptoDataList.size) null else page + 1
            )
        } catch (e: Exception) {
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


}
