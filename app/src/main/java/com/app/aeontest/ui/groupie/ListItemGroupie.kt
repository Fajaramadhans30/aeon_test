package com.app.aeontest.ui.groupie

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aeontest.R
import com.app.aeontest.data.ListModel
import com.app.aeontest.ui.adapter.PhotoListAdapter
import com.kennyc.view.MultiStateView
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_main.view.*

class ListItemGroupie (private val context: Context,
private var datas: List<ListModel>,
private val onListClicked: ((listModel: ListModel) -> Unit)? = null
) : Item() {

    var viewState = MultiStateView.ViewState.LOADING

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            itemView.msvList.viewState = viewState
            val photoListAdapter = PhotoListAdapter(context, datas, onListClicked)

            itemView.rvList.apply {
                val gridLayoutManager = GridLayoutManager(context, 2)
                gridLayoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager = gridLayoutManager
                adapter = photoListAdapter
            }
        }
    }


    override fun getLayout(): Int = R.layout.activity_main

    fun add(datas: List<ListModel>) {
        this.datas = datas
    }
}