package com.example.myapplication.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.myapplication.data.Response
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Url
import java.io.Serializable

interface ApiService {
    @GET("/api/character")
    suspend fun loadList(@Query(value = "page") page: Int): Response
}

