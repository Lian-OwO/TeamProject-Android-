package com.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my_universe.mainAdapter.CategoryAdapter
import com.my_universe.mainFragment.HomeFragment
import com.my_universe.mainFragment.MapFragment
import com.my_universe.mainFragment.WishFragment
import com.my_universe.databinding.ActivityMainBinding
import com.my_universe.model.CategoryItem
import com.my_universe.utils.SharedPreferencesManager
import com.my_universe.utils.SharedPreferencesManager.getLoginStatus
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

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when (tab?.text) {
                    "홈" -> {
                        transaction.replace(R.id.tabContent, HomeFragment())
                        binding.header.visibility = View.VISIBLE
                    }
                    "위시" -> {
                        transaction.replace(R.id.tabContent, WishFragment())
                        binding.header.visibility = View.VISIBLE
                    }
                    "맵" -> {
                        transaction.replace(R.id.tabContent, MapFragment())
                        binding.header.visibility = View.VISIBLE
                    }
                    "마이" -> {
                        transaction.replace(R.id.tabContent, MyPageActivity())
                        binding.header.visibility = View.GONE
                    }
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "onTabUnselected", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "onTabReselected", Toast.LENGTH_SHORT).show()
            }
        })

        // 화면 다시 그리기
        invalidateOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        updateMenuVisibility(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                startActivity(Intent(this, LoginFormActivity::class.java))
                true
            }
            R.id.action_logout -> {
                // 로그아웃 처리 (Firebase 사용 시 필요한 코드 추가)
                SharedPreferencesManager.saveName(this@MainActivity, null)
                SharedPreferencesManager.saveEmail(this@MainActivity, null)
                SharedPreferencesManager.saveToken(this@MainActivity, null)
                SharedPreferencesManager.saveLoginStatus(this@MainActivity, false)
                // 로그아웃 후 로그인 화면으로 이동 또는 다른 처리 수행
                startActivity(Intent(this, LoginFormActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateMenuVisibility(menu: Menu) {
        // 현재 사용자의 로그인 상태에 따라 메뉴 아이템 가시성 설정
        menu.findItem(R.id.action_login)?.isVisible = !getLoginStatus(this@MainActivity)!!
        Log.d("로그인 후 테스트", getLoginStatus(this@MainActivity).toString())
        menu.findItem(R.id.action_logout)?.isVisible = getLoginStatus(this@MainActivity)!!
    }

    private fun initializeRecyclerView() {
        // RecyclerView 초기화 코드 (이미 구현되어 있음)
        val categoryRecyclerView: RecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val categoryItems = listOf(
            CategoryItem(R.drawable.category_gangseo, "강서구"),
            CategoryItem(R.drawable.category_geumjeong, "금정구"),
            CategoryItem(R.drawable.category_gijang, "기장군"),
            CategoryItem(R.drawable.category_dongnae, "동래구"),
            CategoryItem(R.drawable.category_busanjin, "부산진구"),
            CategoryItem(R.drawable.category_saha, "사하구"),
            CategoryItem(R.drawable.category_suyeong, "수영구"),
            CategoryItem(R.drawable.category_haeundae, "해운대구"),
        )

        val categoryAdapter = CategoryAdapter(categoryItems)
        categoryRecyclerView.adapter = categoryAdapter
    }
}
