package com.app.aeontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aeontest.data.ListModel
import com.app.aeontest.data.realm.DataRealm
import com.app.aeontest.network.remote.Resource
import com.app.aeontest.ui.adapter.PhotoListAdapter
import com.app.aeontest.viewModel.ViewModell
import com.kennyc.view.MultiStateView
import com.skydoves.whatif.whatIfNotNull
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModell: ViewModell by viewModel()

    private lateinit var realm: Realm
    private lateinit var dataRealm: ArrayList<DataRealm>
    private var listModel: ListModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModell.getListData()
        observeData()

        dataRealm = ArrayList()
        dataRealm.clear()
        getDataFromRealm()
        addToRealm()
    }

    private fun getDataFromRealm() {
        /*REALM*/
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("data_realm.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
        realm = Realm.getDefaultInstance()

        val result: RealmResults<DataRealm> = realm.where<DataRealm>(DataRealm::class.java).findAll()

        val photoListAdapter = PhotoListAdapter(
            context = this,
            items =  result
            )
        rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = photoListAdapter
        }

    }

    private fun observeData() {
        viewModell.dataListModel.observe(this, Observer {
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Empty -> showEmpty()
                is Resource.Success -> showContent(it.data)
                is Resource.Error -> showError()
            }
        })
    }

    private fun showLoading(){
        MultiStateView.ViewState.LOADING
    }

    private fun showEmpty() {
        MultiStateView.ViewState.EMPTY
    }


    private fun showContent(data: List<ListModel>) {
        MultiStateView.ViewState.CONTENT
        for (i in data) {
            saveDataToRealm(i.albumId, i.id, i.title, i.url, i.thumbnailUrl)
        }
    }

    private fun showError() {
        MultiStateView.ViewState.ERROR

    }
    private fun saveDataToRealm(
        albumId: String,
        id: String,
        title: String,
        url: String,
        thumbnailUrl: String
    ) {
        realm.beginTransaction()
        val currentIdNumber: Number? = realm.where(DataRealm::class.java).max("parentId")

        val nextID: Int = if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toInt() + 1
        }

        val listRealm = DataRealm()
        listRealm.parentId = nextID
        listRealm.albumId = albumId
        listRealm.id = id
        listRealm.title = title
        listRealm.url = url
        listRealm.thumbnailUrl = thumbnailUrl

        realm.copyToRealmOrUpdate(listRealm)
        realm.commitTransaction()

    }

    private fun addToRealm() {
        /*REALM*/
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("data_realm.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
        realm = Realm.getDefaultInstance()

        listModel.whatIfNotNull {
            listModel?.apply {
                saveDataToRealm(
                    it.albumId,
                    it.id,
                    it.title,
                    it.url,
                    it.thumbnailUrl
                )
            }
        }

    }
}