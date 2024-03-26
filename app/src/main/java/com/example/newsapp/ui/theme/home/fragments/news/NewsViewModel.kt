package com.example.newsapp.ui.theme.home.fragments.news

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.APIManager
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.Category
import com.example.newsapp.model.SourcesItem
import com.example.newsapp.model.SourcesResponse
import com.example.newsapp.ui.theme.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    var tabsLiveData = MutableLiveData<List<SourcesItem?>>()
    var progressVisibilityLiveData = MutableLiveData<Boolean>()
    var articlesLiveData = MutableLiveData<List<Article?>>()

    fun getTabs(selectedCategory: Category) {

        progressVisibilityLiveData.value = true
        APIManager
            .getWebServices()
            .getSources(
                Constants.API_KEY,
                selectedCategory!!.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {

                    Log.e("onResponse","${response.body()}")

                    progressVisibilityLiveData.value = false
                    if (response.body()?.code == null) {
                        tabsLiveData.value = response.body()!!.sources!!
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressVisibilityLiveData.value = false
//                    Log.e("onFailure","$t")
//                    Toast.makeText(requireActivity(),"Something went wrong please try again later!",
//                        Toast.LENGTH_LONG).show()
                }

            })
    }

        fun getArticles(tabId : String) {

        progressVisibilityLiveData.value = true
        APIManager
            .getWebServices()
            .getArticles(
                Constants.API_KEY,
                tabId
            ).enqueue(object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    progressVisibilityLiveData.value = false

                    if (response?.code() in 200 .. 299 ) {
                        articlesLiveData.value = response.body()!!.articles!!
                    }



                }

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    progressVisibilityLiveData.value = false
//                    Log.e("onFailure","$t")
//                    Toast.makeText(requireActivity(),"Something went wrong please try again later!",
//                        Toast.LENGTH_LONG).show()
                }

            })

    }
}