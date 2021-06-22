package com.app.aeontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aeontest.data.ListModel
import com.app.aeontest.network.remote.Resource
import com.app.aeontest.ui.groupie.ListItemGroupie
import com.app.aeontest.viewModel.ViewModell
import com.kennyc.view.MultiStateView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModell: ViewModell by viewModel()

    private val groupieAdapter: GroupAdapter<GroupieViewHolder> by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val section : Section by lazy {
        Section()
    }

    private var listItemGroupie: ListItemGroupie ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGroupie()
        viewModell.getListData()
        observeData()
    }

    private fun initGroupie() {
        listItemGroupie = ListItemGroupie(this, mutableListOf()) {

            initContent()
        }
    }

    private fun initContent() {
        section.add(listItemGroupie!!)

        groupieAdapter.add(section)

        rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupieAdapter
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
        listItemGroupie?.viewState = MultiStateView.ViewState.LOADING

        groupieAdapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        listItemGroupie?.viewState = MultiStateView.ViewState.EMPTY
    }


    private fun showContent(data: List<ListModel>) {
        listItemGroupie?.viewState = MultiStateView.ViewState.CONTENT
        showData(data)
        groupieAdapter.notifyDataSetChanged()


    }

    private fun showError() {
        listItemGroupie?.viewState = MultiStateView.ViewState.ERROR

    }

    private fun showData(data: List<ListModel>) {
        listItemGroupie?.add(data)
    }
}