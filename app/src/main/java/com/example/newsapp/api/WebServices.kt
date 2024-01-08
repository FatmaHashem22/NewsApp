package com.example.newsapp.api

import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("apiKey") apiKeyAuthentication : String) : Call<SourcesResponse>

    @GET("v2/everything")
    fun getArticles(
        @Query("apiKey") apiKeyAuthentication : String,
        @Query("sources") tab : String

        ) : Call<ArticlesResponse>
}