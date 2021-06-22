package com.app.aeontest.network.remote

import com.app.aeontest.data.ListModel

class Api(private val apiService: ApiService) : ApiService {
    override suspend fun getList(): List<ListModel> {
        return apiService.getList()
    }
}