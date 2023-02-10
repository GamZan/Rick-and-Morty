package com.example.myapplication.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.CharacterFromRickAndMorty
import com.example.myapplication.data.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterViewModel (
    private val repository: CharacterRepository,
    private val throwable: MutableLiveData<Throwable?>
) : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val pageCharacter: Flow<PagingData<CharacterFromRickAndMorty>> = Pager(
        config = PagingConfig(pageSize = 5),
        initialKey = null,
        pagingSourceFactory = { CharacterPagingSource(throwable) }
    ).flow.cachedIn(viewModelScope)

    init {
        pageCharacter
    }
}