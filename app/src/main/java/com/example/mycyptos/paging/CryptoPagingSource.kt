package com.example.mycyptos.paging

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.datamodels.CryptoData
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.utils.AppConstants
import javax.inject.Inject

class CryptoPagingSource @Inject constructor(
    val apiService: CryptoApiService,val route: String, val context: Context
) : PagingSource<Int,Data>() {

    private lateinit var cryptoData: CryptoData
    private lateinit var cryptoDataList: List<Data>
    private var topRankedCryptoData: Data ?= null
    private var mutableSortedDataList: MutableList<Data> ?= mutableListOf()
    private var sortedDataList : List<Data> = listOf()

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
            Log.d("api route", route + AppConstants.all_crypto_list_key)

            if(route.equals(AppConstants.all_crypto_list_key)) {
                sortedDataList = cryptoDataList
            }
            else if(route.equals(AppConstants.fav_crypto_list_key)) {
                mutableSortedDataList = getFavCryptoList(cryptoDataList,context)
                sortedDataList = mutableSortedDataList!!
            }
            else if(route.equals(AppConstants.first_crypto_key)) {
                topRankedCryptoData = evaluateTopRankedCrypto(cryptoDataList)
                mutableSortedDataList?.add(topRankedCryptoData!!)
                sortedDataList = mutableSortedDataList!!
            }

            Log.d("api sorted list", sortedDataList.toString())
            LoadResult.Page(
                data = sortedDataList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (sortedDataList.isEmpty() || (page*params.loadSize)>=sortedDataList.size) null else page + 1
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

    fun getFavCryptoList(cryptoDataList: List<Data>, context: Context) : MutableList<Data>?{
        val sharedPreferences = context?.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        var value = sharedPreferences?.getString("fav","")
        val editor = sharedPreferences?.edit()
        val mutableCryptoList : MutableList<Data> = mutableListOf()

        val mutableSet : MutableSet<String> = mutableSetOf()
        if (value != null) {
            var symbol = ""
            value.forEach { char ->
                if(char.equals(',')) {
                    mutableSet.add(symbol)
                    symbol = ""
                } else {
                    symbol += char
                }
            }
        }

        cryptoDataList.forEach {
            val cryptoData = it
            mutableSet.forEach {
                if(it.equals(cryptoData.symbol.toString())) {
                    mutableCryptoList?.add(cryptoData)
                }
            }
        }

        return mutableCryptoList
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
