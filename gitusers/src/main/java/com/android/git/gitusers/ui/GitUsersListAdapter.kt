package com.android.git.gitusers.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.android.git.gitusers.ui.model.SearchedUsersViewData
import com.android.gitusers.R
import javax.inject.Inject

class GitUsersListAdapter @Inject constructor() : RecyclerView.Adapter<GitUsersListViewHolder>() {

    private var items = mutableListOf<SearchedUsersViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitUsersListViewHolder =
        GitUsersListItemViewHolder(inflateView(parent, R.layout.list_item))

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes layout: Int) =
        LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holderGit: GitUsersListViewHolder, position: Int) {
        holderGit.bindItem(items[position])
    }

    fun setData(list: List<SearchedUsersViewData>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }
}