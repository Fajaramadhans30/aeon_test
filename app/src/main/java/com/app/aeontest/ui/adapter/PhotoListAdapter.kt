package com.app.aeontest.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.aeontest.R
import com.app.aeontest.data.realm.DataRealm
import com.app.aeontest.util.showImageUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_list.view.*

class PhotoListAdapter(private val context: Context,
                       private val items: RealmResults<DataRealm>
) : RecyclerView.Adapter<PhotoListAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        for (data in items) {
            holder.bind(data)
            Log.d("TAG", "onBindViewHolder: " + data.title)
        }
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataRealm) {
            with(itemView) {
//                data.thumbnailUrl?.let { ivIcon.showImageUrl(context, it) }
                val requestOption = RequestOptions()
                requestOption.placeholder(R.drawable.ic_launcher_background)
                requestOption.error(R.drawable.ic_launcher_background)
                Glide.with(itemView.ivIcon).setDefaultRequestOptions(requestOption)
                    .load(data.url).into(itemView.ivIcon)
                tvTitle.text = data.title
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}