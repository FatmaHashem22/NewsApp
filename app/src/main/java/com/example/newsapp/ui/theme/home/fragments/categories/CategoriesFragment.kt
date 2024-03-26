package com.example.newsapp.ui.theme.home.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Category


class CategoriesFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    lateinit var adapter: CategoriesAdapter

    val categories : List<Category> = listOf(
        Category("sports", "Sports", R.drawable.sports_image, R.color.red, true),
        Category("entertainment", "Entertainment", R.drawable.politics_image, R.color.blue, false),
        Category("health", "Health", R.drawable.health_image, R.color.pink, true),
        Category("business", "Business", R.drawable.bussines_image, R.color.brown, false),
        Category("technology", "Technology", R.drawable.environment_image, R.color.light_blue, true),
        Category("science", "Science", R.drawable.science_image, R.color.yellow, false )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    fun initViews(view : View) {
        recyclerView = view.findViewById(R.id.categories_recycler_view)
        adapter = CategoriesAdapter(categories)
        adapter.onCategoryClick = object : CategoriesAdapter.OnCategoryClick {
            override fun onCategoryClick(category: Category, position: Int) {
                onCategoryClick?.onCategoryClick(category)
            }

        }
        recyclerView.adapter = adapter

    }

    var onCategoryClick : OnCategoryClick? = null

    interface OnCategoryClick {
        fun onCategoryClick( category: Category)
    }


}