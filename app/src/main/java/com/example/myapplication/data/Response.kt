package com.example.myapplication.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Response(
    @SerializedName("results")
    val results: List<CharacterFromRickAndMorty>
) : Serializable
