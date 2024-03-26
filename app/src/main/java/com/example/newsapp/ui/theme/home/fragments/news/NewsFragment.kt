package com.example.newsapp.ui.theme.home.fragments.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.api.APIManager
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.Category
import com.example.newsapp.model.SourcesItem
import com.example.newsapp.model.SourcesResponse
import com.example.newsapp.ui.theme.Constants
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment() : Fragment() {

    lateinit var tabLayout : TabLayout
    lateinit var newsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var viewModel: NewsViewModel

    var selectedCategory : Category? = null
    var page : Int = 1
    var adapter : ArticleAdapter = ArticleAdapter(listOf())

    companion object {
        fun getInstance(category: Category): NewsFragment {
            val newsFragment = NewsFragment()
            newsFragment.selectedCategory = category
            return newsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initListeners()
        observeViewModel()
        viewModel.getTabs(selectedCategory!!)

    }

    fun observeViewModel() {
        viewModel.tabsLiveData.observe(viewLifecycleOwner) { tabs ->
            if(tabs != null) {
                addTabs(tabs)
            }
        }

        viewModel.progressVisibilityLiveData.observe(viewLifecycleOwner) {
            if (it) {
                progressBar.isVisible = true
            }
            else {
                progressBar.isVisible = false
            }
        }

        viewModel.articlesLiveData.observe(viewLifecycleOwner) {
            adapter.changeData(it)
        }

    }

    fun initViews(view: View){
        tabLayout = view.findViewById(R.id.tab_layout)
        newsRecyclerView = view.findViewById(R.id.news_list_recycler_view)
        progressBar = view.findViewById(R.id.progressBar)
        newsRecyclerView.adapter = adapter

    }

    fun initListeners() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab!!.tag as String
                viewModel.getArticles(id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val id = tab!!.tag as String
                viewModel.getArticles(id)
            }

        })
    }





    private fun addTabs(sources: List<SourcesItem?>) {

        sources.forEach{ tab ->
            val newTab = tabLayout.newTab()
            newTab.text = tab?.name
            newTab.tag = tab?.id?: ""
            tabLayout.addTab(newTab)
        }
    }

}