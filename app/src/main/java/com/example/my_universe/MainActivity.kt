package com.example.my_universe


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.MainFragment.MapFragment
import com.example.my_universe.MainFragment.Test1Fragment
import com.example.my_universe.databinding.ActivityMainBinding
import com.example.my_universe.model.CategoryItem
import com.example.my_universe.recycler.CategoryAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val tabLayout = binding.tabs
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initializeRecyclerView()

        tabLayout.addOnTabSelectedListener( object: TabLayout.OnTabSelectedListener {
            //익명 클래스 정의하고, 해당 이벤트 리스너 구현하면, 의무적으로,
            // 재정의 해야하는 함수3개 있음.
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "home" -> transaction.replace(R.id.tabContent, Test1Fragment())
                    "wish" -> transaction.replace(R.id.tabContent, Test1Fragment())
                    "map" -> transaction.replace(R.id.tabContent, MapFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity,"onTabUnselected", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity,"onTabReselected", Toast.LENGTH_SHORT).show()
            }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                startActivity(Intent(this, LoginFormActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeRecyclerView() {
        // RecyclerView 초기화 코드 (이미 구현되어 있음)
        val categoryRecyclerView: RecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)




        val categoryItems = listOf(
            CategoryItem(R.drawable.category, "강서구"),
            CategoryItem(R.drawable.category, "사하구"),
            CategoryItem(R.drawable.category, "사상구"),
            CategoryItem(R.drawable.category, "북구"),
            CategoryItem(R.drawable.category, "부산진구"),
            CategoryItem(R.drawable.category, "동래구"),
            CategoryItem(R.drawable.category, "금정구"),
            CategoryItem(R.drawable.category, "해운대구"),
            CategoryItem(R.drawable.category, "기장군"),
        )

        val categoryAdapter = CategoryAdapter(categoryItems)
        categoryRecyclerView.adapter = categoryAdapter



    }


}
