package com.my_universe.apiModel

data class BoardListModel(


    val header : String,

    val pageNo: String,

    val numOfRows: String,

    val totalCount: String,

    val item:  List <BoardModel>
)
