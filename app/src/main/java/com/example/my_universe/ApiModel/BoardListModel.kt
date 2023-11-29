package com.example.my_universe.ApiModel

data class BoardListModel(


    val header : String,

    val pageNo: String,

    val numOfRows: String,

    val totalCount: String,

    val item:  List <BoardModel>
)
