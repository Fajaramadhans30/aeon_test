package com.app.aeontest.network.remote

import com.app.aeontest.data.ListModel
import retrofit2.http.GET

interface ApiService{
    @GET("/photos")
    suspend fun getList(): ListModel
}