package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_universe.MainFragment.HomeFragment
import com.example.my_universe.MainFragment.MapFragment
import com.example.my_universe.MainFragment.WishFragment
import com.example.my_universe.databinding.ActivityMainBinding
import com.example.my_universe.model.CategoryItem
import com.example.my_universe.recycler.CategoryAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isLoggedIn: Boolean = false
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                    "홈" -> transaction.replace(R.id.tabContent, HomeFragment())
                    "위시" -> transaction.replace(R.id.tabContent, WishFragment())
                    "맵" -> transaction.replace(R.id.tabContent, MapFragment())
                    "마이" -> transaction.replace(R.id.tabContent, MyPageActivity())
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

        // 로그인 상태 확인
        checkLoginStatus()
    }

    override fun onResume() {
        super.onResume()

        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val currentUser: FirebaseUser? = auth.currentUser
        isLoggedIn = (auth.currentUser != null)
        invalidateOptionsMenu() // 메뉴 다시 그리기
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
                auth.signOut()
                // 로그아웃 후 로그인 화면으로 이동 또는 다른 처리 수행
                startActivity(Intent(this, LoginFormActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateMenuVisibility(menu: Menu) {
        // 현재 사용자의 로그인 상태에 따라 메뉴 아이템 가시성 설정
        menu.findItem(R.id.action_login)?.isVisible = !isLoggedIn
        menu.findItem(R.id.action_logout)?.isVisible = isLoggedIn
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
