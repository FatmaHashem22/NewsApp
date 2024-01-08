package com.example.newsapp.ui.theme

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.api.APIManager
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.SourcesItem
import com.example.newsapp.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    lateinit var tabLayout : TabLayout
    lateinit var newsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    var adapter : ArticleAdapter = ArticleAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        getTabs()
        initListeners()


    }
    fun initViews(){
        tabLayout = findViewById(R.id.tab_layout)
        newsRecyclerView = findViewById(R.id.news_list_recycler_view)
        progressBar = findViewById(R.id.progressBar)
        newsRecyclerView.adapter = adapter

    }

    fun initListeners() {
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab!!.tag as String
                getArticles(id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val id = tab!!.tag as String
                getArticles(id)
            }

        })
    }

    private fun getArticles(tabId : String) {

        progressBar.isVisible = true
        APIManager
            .getWebServices()
            .getArticles(
                Constants.API_KEY,
                tabId
            ).enqueue(object : Callback<ArticlesResponse>{
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    progressBar.isVisible = false
                    Log.e("onResponse","${response.body()?.articles}")
                    adapter.changeData(response.body()?.articles!!)

                }

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    Log.e("onFailure","$t")
                    Toast.makeText(this@MainActivity,"Something went wrong please try again later!",Toast.LENGTH_LONG).show()
                }

            })

    }

    fun getTabs() {
        APIManager
            .getWebServices()
            .getSources(Constants.API_KEY)
            .enqueue(object : Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible = false
                    Log.e("onResponse","${response.body()}")
                    if (response.body()?.code == null) {
                        addTabs(response.body()?.sources!!)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    Log.e("onFailure","$t")
                    Toast.makeText(this@MainActivity,"Something went wrong please try again later!",Toast.LENGTH_LONG).show()
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

