package com.my_universe.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my_universe.MyApplication
import com.my_universe.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

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
//                val lnt: Double =  126.4160275
//                val        lat = 33.458271995622
//                val     lnt = 126.47883418947458
        val lat = 35.1560157
        val lnt = 129.0594088
                marker.setPosition(LatLng(lat, lnt))
                marker.setMap(naverMap)

                val cameraPosition = CameraPosition( // 카메라 위치 변경
                    LatLng(lat,lnt), // 위치 지정
                    16.0 // 줌 레벨
                )
                naverMap?.cameraPosition = cameraPosition


        // 두 번째 마커
        val lat2 = 33.458271995622
        val lng2 = 126.47883418947458
        val marker2 = Marker()
        marker2.position = LatLng(lat2, lng2)
        marker2.map = naverMap

        // 마커 위치 정보 리스트
        val markerPositions = listOf(
            LatLng(35.1560157, 129.0594088),
            LatLng(35.1575, 129.0600),
            LatLng(35.1562749, 129.0582212),
            LatLng(35.1552703, 129.0601868),
            LatLng(35.1557237, 129.0597270),
            LatLng(35.1549210, 129.0589133),
            LatLng(35.1556577, 129.0581568),
            LatLng(35.1560634, 129.0583441),
            LatLng(35.1565990, 129.0601929),
            LatLng(35.1569666, 129.0589334),
            LatLng(35.1569750, 129.0612130),
            LatLng(35.1555571, 129.0594493),
            LatLng(35.1570440, 129.0599122),
            LatLng(35.1558323, 129.0602433),
            LatLng(35.1546588, 129.0600349),
            LatLng(35.1565704, 129.0584100)
            // 추가적인 위치 정보는 필요에 따라 계속 추가할 수 있습니다.
        )

// 반복문을 통해 마커를 추가
        for (position in markerPositions) {
            val marker = Marker()
            marker.position = position
            marker.map = naverMap
        }



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