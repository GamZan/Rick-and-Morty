package com.example.myapplication.presentation

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.CharacterFromRickAndMorty
import com.example.myapplication.data.CharacterRepository

class CharacterPagingSource(private val throwable: MutableLiveData<Throwable?>) :
    PagingSource<Int, CharacterFromRickAndMorty>() {

    private val repository = CharacterRepository()

    override fun getRefreshKey(state: PagingState<Int, CharacterFromRickAndMorty>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterFromRickAndMorty> {
            val page = params.key ?: FIRST_PAGE
            return kotlin.runCatching {
                repository.getCharacters(page)
            }.fold (
                onSuccess = {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else page + FIRST_PAGE
                    )
                },
                onFailure = { exception: Throwable ->
                    throwable.postValue(exception)
                    LoadResult.Error(exception)
                }
            )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
