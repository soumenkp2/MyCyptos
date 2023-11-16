package com.example.mycyptos.data.apiservice

import com.example.mycyptos.datamodels.CryptoData
import com.example.mycyptos.utils.AppConstants
import retrofit2.http.GET

interface CryptoApiService {

    //https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=ff868612-5cfe-49f2-8e53-7fb6d5dc54fd&sort=price&sort_dir=desc
    @GET("v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=${AppConstants.API_KEY}")
    suspend fun getCryptoDataList() : CryptoData

}
