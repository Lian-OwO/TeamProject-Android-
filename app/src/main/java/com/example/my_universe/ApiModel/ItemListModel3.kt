package com.example.my_universe.ApiModel

class ItemListModel3 (
    // 변수명, 넘어온 데이터 변수명 일치하기.
    val getWalkingKr : GetWalkingKr
)

class GetWalkingKr (
    val item : List<ItemModel4>?
)

class ItemModel4 {
    var MAIN_TITLE : String? = null
    var TITLE : String? = null
    var MAIN_IMG_NORMAL : String? = null
}