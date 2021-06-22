package com.app.aeontest.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.aeontest.data.ListModel
import com.app.aeontest.network.remote.Repository
import com.app.aeontest.network.remote.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModell(
    private val repository: Repository
) : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val dataListModel = MutableLiveData<Resource<List<ListModel>>>()

    fun getListData() {
        dataListModel.value = Resource.loading()
        uiScope.launch {
            try {
                val datas = repository.getListData()
                dataListModel.value = Resource.success(datas)
            } catch (e: Exception) {
                dataListModel.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }

}