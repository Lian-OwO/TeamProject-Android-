package com.my_universe.model

data class BoardItem(
    val title: String?,
    val subTitle: String?,
    val content: String?,
    val images: MutableMap<String, String?> // 수정된 부분
) {
    // 추가된 부분: 이미지 추가하는 메서드
    fun addImage(key: String, value: String?) {
        images[key] = value
    }

    // 추가된 부분: 이미지 가져오는 메서드
    fun getImage(key: String): String? {
        return images[key]
    }
}