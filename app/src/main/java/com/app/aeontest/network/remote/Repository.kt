package com.app.aeontest.network.remote

import com.app.aeontest.data.ListModel

interface Repository {
    suspend fun getListData():List<ListModel>

}