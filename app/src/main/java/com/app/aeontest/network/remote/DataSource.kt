package com.test.kecipirtest.network.remote

import com.app.aeontest.data.ListModel
import com.app.aeontest.network.remote.Api
import com.app.aeontest.network.remote.Repository


class DataSource(val api: Api) : Repository {
    override suspend fun getListData(): List<ListModel> {
        return listOf(api.getList())
    }

}