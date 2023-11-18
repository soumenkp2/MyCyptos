package com.example.mycyptos.presentation.favcrypto

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
import com.example.mycyptos.domain.FavCryptoUsecase
import com.example.mycyptos.domain.TopCryptoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavCryptoViewModel @Inject constructor(private val favCryptoUsecase: FavCryptoUsecase) : ViewModel() {
    private val _pagingData = MutableLiveData<PagingData<Data>>()
    val pagingData: LiveData<PagingData<Data>> get() = _pagingData

    fun getFavCryptoData() = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            val messageSource = favCryptoUsecase.invoke()
            val pager = Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { messageSource }
            )

            val pagingData = pager.flow.cachedIn(viewModelScope)
            pagingData.asLiveData().observeForever {
                _pagingData.postValue(it)
            }


        }
    }
}