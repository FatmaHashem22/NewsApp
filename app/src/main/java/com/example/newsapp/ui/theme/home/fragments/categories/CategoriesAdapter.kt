package com.example.newsapp.ui.theme.home.fragments.categories

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapp.R
import com.example.newsapp.model.Category

class CategoriesAdapter(val items: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val categoryTitle : TextView = itemView.findViewById(R.id.category_option)
        val categoryImage : ImageView = itemView.findViewById(R.id.category_image)
        val categoryBg : View = itemView.findViewById(R.id.item_background)

    }


    override fun getItemViewType(position: Int): Int {

        val category = items.get(position)
        return if (category.isleftSided)
             1
        else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == 1) R.layout.item_left_category
            else R.layout.item_right_category,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = items.get(position)
        holder.categoryImage.setImageResource(category.imageId)
        holder.categoryTitle.text = category.title
        holder.categoryBg.backgroundTintList = ColorStateList.valueOf(category.colorId)

    }

    override fun getItemCount(): Int = items.size



}