package com.example.mycyptos.data.apiservice

import com.example.mycyptos.datamodels.CryptoData
import retrofit2.http.GET

interface CryptoApiService {

    @GET("v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=ff868612-5cfe-49f2-8e53-7fb6d5dc54fd")
    suspend fun getCryptoDataList() : CryptoData

}
