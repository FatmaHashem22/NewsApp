package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticlesResponse

class ArticleAdapter(var articles : List<Article?>) : Adapter<ArticleAdapter.ArticleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_article,parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    fun changeData(newList : List<Article?>) {
        articles = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        var article = articles.get(position)
        holder.authorTextView.text = article?.author
        holder.titleTextView.text = article?.title
        holder.dateTextView.text = article?.publishedAt
        Glide.with(holder.itemView)
            .load(article?.urlToImage)
            .centerCrop()
            .into(holder.image)

    }

    class ArticleViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.articleImage)
        val authorTextView : TextView = item.findViewById(R.id.articleAuthor)
        val titleTextView : TextView = item.findViewById(R.id.articleTitle)
        val dateTextView : TextView = item.findViewById(R.id.articleDate)


    }
}