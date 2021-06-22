package com.app.aeontest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.aeontest.R
import com.app.aeontest.data.ListModel
import com.app.aeontest.util.showImageUrl
import kotlinx.android.synthetic.main.item_list.view.*

class PhotoListAdapter(private val context: Context,
                       private val datas: List<ListModel>,
                       private val onListClicked: ((listModel: ListModel) -> Unit)? = null
) : RecyclerView.Adapter<PhotoListAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: ListModel) {
            with(itemView) {
                ivIcon.showImageUrl(context, data.url)
                tvTitle.text = data.title
                setOnClickListener {
                    onListClicked?.invoke(data)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}