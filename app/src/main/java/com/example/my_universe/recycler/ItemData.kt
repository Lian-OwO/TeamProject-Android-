package com.example.my_universe.recycler

class ItemData {
    // 게시글 번호 역할, 이미지를 불러올 예정(단일 이미지)
    // 게시글 Id 자동으로 생성도 가능하고, 임의로 지정도 가능
    // 일단, 자동id로 이용하기.
    var docId: String? = null
    // 로그인 유저 = 작성자(이메일)
    var email: String? = null
    // 내용.
    var content: String? = null
    // 날짜.
    var date: String? = null
    // img1
    var img1: String? = null
    // img2
    var img2: String? = null
    // img3
    var img3: String? = null
    // img4
    var img4: String? = null
}