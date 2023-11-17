package com.example.mycyptos.presentation.topcrypto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.domain.TopCryptoUsecase
import com.example.mycyptos.paging.DataCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopCryptoViewModel @Inject constructor(private val topCryptoUsecase: TopCryptoUsecase) : ViewModel()
{
    private val _pagingData = MutableLiveData<PagingData<Data>>()
    val pagingData: LiveData<PagingData<Data>> get() = _pagingData

    private val _topRankedCryptoData = MutableLiveData<Data>()
    val topRankedCryptoData: LiveData<Data> get() = _topRankedCryptoData

    val _dataLoaded = MutableLiveData<Boolean>(false)
    val dataLoaded : LiveData<Boolean> get() = _dataLoaded

    val selectedCrypto = MutableLiveData<Data>()

    fun getTopCryptoData() = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            val messageSource = topCryptoUsecase.invoke()
            val pager = Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { messageSource }
            )

            val pagingData = pager.flow.cachedIn(viewModelScope)
            pagingData.asLiveData().observeForever {
                Log.d("api viewmodel dataLoaded",_pagingData.value.toString() + _dataLoaded.value)
                _pagingData.postValue(it)
                _dataLoaded.postValue(true)
                Log.d("api viewmodel dataLoaded",_pagingData.value.toString() + _dataLoaded.value)
            }


        }
    }

    fun getTopRankedCryptoData() = viewModelScope.launch(Dispatchers.Main){
        topCryptoUsecase.getTopRankedCryptoData(object : DataCallback {
            override fun onDataReceived(data: Data) {
                Log.d("apii viewmodel",data.toString() + _dataLoaded.value)
                _topRankedCryptoData.postValue(data)
            }
        })
    }

}