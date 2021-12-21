package com.android.git.gitusers.ui

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.android.git.gitusers.ui.model.SearchedUsersViewData
import com.android.gitusers.R
import com.android.gitusers.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class GitUsersActivity : DaggerAppCompatActivity(), GitUsersContract.View {

    @Inject
    lateinit var presenter: GitUsersContract.Presenter

    @Inject
    lateinit var adapterGit: GitUsersListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidInjection.inject(this)
        setupAdapter()
        setupRecyclerViewScrollListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.d("onQueryTextSubmit: $query")
                query?.let { presenter.subscribeOnGitUsersQuery(it, 1) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d("onQueryTextChange: $newText")
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerViewScrollListener() =
        binding.usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.nextPage()
                }
            }
        })

    private fun setupAdapter() {
        binding.usersRecyclerView.adapter = adapterGit
    }

    override fun setData(data: List<SearchedUsersViewData>) = adapterGit.setData(data)

    override fun hideProgressBar() {
        binding.progressbar.visibility = View.INVISIBLE
    }

    override fun showProgressBar() {
        binding.progressbar.visibility = View.VISIBLE
    }

    override fun hideEmptyPage() {
        binding.emptyPage.visibility = View.INVISIBLE
    }

    override fun showRecyclerView() {
        binding.usersRecyclerView.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        binding.usersRecyclerView.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(errorMsg: String) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT)
            .setTextColor(Color.RED)
            .show()
    }
}