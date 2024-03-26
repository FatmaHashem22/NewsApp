package com.example.newsapp.ui.theme.home

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.ui.theme.home.fragments.categories.CategoriesFragment
import com.example.newsapp.ui.theme.home.fragments.news.NewsFragment
import com.example.newsapp.ui.theme.home.fragments.settings.SettingsFragment

class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout : DrawerLayout
    lateinit var icDrawer : ImageView
    lateinit var categories : TextView
    lateinit var settings : TextView
    lateinit var appBarTitle : TextView
    val categoryFragment = CategoriesFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        pushFragments(categoryFragment)
        initViews()
        initListeners()

    }

    fun initViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        icDrawer = findViewById(R.id.drawer_icon)
        categories = findViewById(R.id.categories_side_menu)
        settings = findViewById(R.id.settings_side_menu)
        appBarTitle = findViewById(R.id.app_bar_title)
        
    }

    fun initListeners() {
        categoryFragment.onCategoryClick = object : CategoriesFragment.OnCategoryClick{
            override fun onCategoryClick(category: Category) {
                pushFragments(NewsFragment.getInstance(category))
            }

        }
        icDrawer.setOnClickListener {
            drawerLayout.open()
        }
        categories.setOnClickListener{
            pushFragments(categoryFragment)
            drawerLayout.close()
            appBarTitle.text = "Categories"

        }
        settings.setOnClickListener{
            pushFragments(SettingsFragment())
            drawerLayout.close()
            appBarTitle.text = "Settings"
        }
    }

    private fun pushFragments(fragment : Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

}