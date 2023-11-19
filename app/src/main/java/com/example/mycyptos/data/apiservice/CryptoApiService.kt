package com.example.mycyptos.data.apiservice

import com.example.mycyptos.datamodels.CryptoData
import com.example.mycyptos.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    /**
     * Retrofit function for making a GET request to the cryptocurrency data API.
     * Endpoint: "v1/cryptocurrency/listings/latest"
     * Parameters: apiKey - API key for authentication, sort - Sorting criteria, sortDir - Sorting direction (e.g., ascending or descending)
     * Returns: CryptoData object representing the API response.
     */
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoDataList(
        @Query("CMC_PRO_API_KEY") CMC_PRO_API_KEY : String,
        @Query("sort") sort : String,
        @Query("sort_dir") sort_dir : String,
        ) : CryptoData

}
