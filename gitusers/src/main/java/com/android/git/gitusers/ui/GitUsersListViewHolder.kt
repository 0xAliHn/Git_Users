package com.android.git.gitusers.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.git.gitusers.ui.model.SearchedUsersViewData
import com.android.gitusers.R
import com.android.gitusers.databinding.ListItemBinding
import com.squareup.picasso.Picasso

sealed class GitUsersListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ListItemBinding.bind(view.rootView)

    abstract fun bindItem(data: SearchedUsersViewData)
}

class GitUsersListItemViewHolder(view: View) : GitUsersListViewHolder(view) {
    override fun bindItem(data: SearchedUsersViewData) {
        Picasso.get().load(data.avatar_url).resize(200, 0).placeholder(R.mipmap.ic_launcher).into(binding.image)
        binding.name.text = data.login
    }
}
