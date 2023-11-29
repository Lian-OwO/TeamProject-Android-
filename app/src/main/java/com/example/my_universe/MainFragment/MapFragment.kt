package com.example.my_universe.MainFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_universe.MyApplication
import com.example.my_universe.R
import com.example.my_universe.databinding.FragmentMapBinding
import com.example.my_universe.databinding.FragmentTest1Binding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), OnMapReadyCallback {
    lateinit var binding: FragmentMapBinding
    private var mapView: com.naver.maps.map.MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMapBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater,container,false)


        //네이버 지도
//        mapView = findViewById<View>(R.id.map_view) as com.naver.maps.map.MapView
        mapView = binding.mapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this@MapFragment)

        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        val marker = Marker()
//                val lat: Double = 33.4840605
//        val lnt: Double =  126.4160275
val        lat = 33.458271995622
   val     lnt = 126.47883418947458
                marker.setPosition(LatLng(lat, lnt))
                marker.setMap(naverMap)

                val cameraPosition = CameraPosition( // 카메라 위치 변경
                    LatLng(lat,lnt), // 위치 지정
                    10.0 // 줌 레벨
                )
                naverMap?.cameraPosition = cameraPosition

        // 데이터 정보에서, 위도 , 경도 값 받아와서 입력.
        // 더미로 .
        // 마커 객체 생성
        val networkService = (context?.applicationContext as MyApplication).map_networkService

//        val lat: Double = 33.4840605
//        val lnt: Double =  126.4160275
////        val mapCall = networkService.reverseGeocode("128.12345,37.98776","json")
//        val mapCall = networkService.reverseGeocode("126.4160275,33.4840605","json")
//        mapCall.enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                // 가져온 위도, 경도 값으로 position 세팅
//                val marker = Marker()
//                marker.setPosition(LatLng(lat, lnt))
//                marker.setMap(naverMap)
//
//                val cameraPosition = CameraPosition( // 카메라 위치 변경
//                    LatLng(lat,lnt), // 위치 지정
//                    10.0 // 줌 레벨
//                )
//                naverMap?.cameraPosition = cameraPosition
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                Log.d("lsy","지도 못받음.")
//            }
//
//        })



        // o9 "itemsLatitude": 33.4840605,
// @SerializedName("itemsLatitude")
// var itemsLatitude : Double,
//
// // 10 "itemsLongitude": 126.4160275,
// @SerializedName("itemsLongitude")
// var itemsLongitude : String,




    }
    companion object {
        private val naverMap: NaverMap? = null
    }

}