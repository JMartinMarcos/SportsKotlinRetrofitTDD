package com.apps.jmm.sportskotlinretrofittdd.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.apps.jmm.sportskotlinretrofittdd.R
import com.apps.jmm.sportskotlinretrofittdd.app.inflate
import com.apps.jmm.sportskotlinretrofittdd.app.loadUrl
import com.apps.jmm.sportskotlinretrofittdd.model.Player
import kotlinx.android.synthetic.main.item_layout.view.*

class PlayersListAdapter(val context: Context?, private val players: List<Player> = emptyList(), private var type: Boolean = false) : RecyclerView.Adapter<PlayersListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlayersListAdapter.ItemViewHolder {
        val itemViewHolder = parent?.inflate(if (type) R.layout.item_layout_grid else R.layout.item_layout)
        return ItemViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        holder?.bind(players[position])
    }

    override fun getItemCount(): Int {
        return players.size
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Player) = with(itemView) {
            media_title.text = "${item.name} ${item.surname}"
            media_thumb.loadUrl(item.image, R.drawable.ic_launcher_foreground)
        }
    }
}
