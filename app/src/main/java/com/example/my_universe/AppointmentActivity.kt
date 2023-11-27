package com.example.my_universe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.databinding.ActivityAppointmentBinding
//import com.example.my_universe.recycler.AppointmentAdapter


//import com.example.my_universe.recycler.AppointmentAdapter

class AppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<String>()

        for (i in 1..3) {
            datas.add("침실   \n퀸사이즈침대 $i")
        }

////        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        binding.recyclerView.adapter = AppointmentAdapter(datas)
//        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
//    }

    }
}