package com.example.myapplication.data

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback

class CharacterRepository {
    private lateinit var list: List<CharacterFromRickAndMorty>

    suspend fun getCharacters(page: Int): List<CharacterFromRickAndMorty> {
        return ApiClient.getApiClient().loadList(page).results
//            .enqueue(object : Callback<Response> {
//            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
//                if (response.code() == 200) {
//                    list = response.body()?.results ?: return
//                }
//            }
//
//            override fun onFailure(call: Call<Response>, t: Throwable) {
//
//            }
//
//        })
//        return list
    }
}


