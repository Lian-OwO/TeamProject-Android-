package com.my_universe.apiModel

// 받아온 데이터 형식을 보고 정해야함.
// 중요함.
///{
//    "getWalkingKr": {
//        "header": {
//        "code": "00",
//        "message": "NORMAL_CODE"
//    },
//        "item": [
//        {
//            "UC_SEQ": 58,
//            "MAIN_TITLE": "초량이바구길",
//            "CATE2_NM": "도보여행",
//            "LAT": 35.11635,
//            "GUGUN_NM": "동구",
//            "LNG": 129.03874,
//            "PLACE": "초량이바구길",
//            "TITLE": "이야기로 피어난 어제의 기억 초량이바구길",
//            "SUBTITLE": "이바구 꽃이 피었습니다",
//            "TRFC_INFO": "도시철도 1호선 부산역 7번 출구 도보 2분, 초량역 1번 출구 도보 8분\n버스 26, 27, 40, 41, 59, 81, 87, 103, 1003, 1004 부산역 하차 도보 2분\n주차 인근 공영주차장",
//            "MIDDLE_SIZE_RM1": "",
//            "MAIN_IMG_NORMAL": "https://www.visitbusan.net/uploadImgs/files/cntnts/20191125133252609_ttiel",
//            "MAIN_IMG_THUMB": "https://www.visitbusan.net/uploadImgs/files/cntnts/20191125133252609_thumbL",
//            "ITEMCNTNTS": "부산항을 기준으로 근처에 보이는 산 중턱마다 죄다 빽빽하게 들어서 있는 주택들.\n정든 고향 남겨두고 부산으로 피난 온 사람들이 산으로 올라가 일군 마을. 일감만 있다면 부두로, 역으로, 국제시장으로 하루에도 수십 번을 오르내렸을 168계단.\n경상도 사투리도 모르던 사람들이 피워낸 이야기 길, 초량이바구길을 만나러 가자. \n\n<p class=\"font-size28 colorDarkBlue medium\">초량이바구길</p><p class=\"font-size20 medium\">옛백제병원 - 남선창고터 - 초량교회 - 168계단 -김민부 전망대 - 이바구공작소 - 장기려더나눔센터 - 유치환우체통 전망대</p>\n부산역을 빠져나오면 부산의 특별한 이야기가 시작된다. 부산역사 길 건너 좁은 골목길에서 만나는 옛백제병원과 남선창고터가 초량이바구길의 출발점이다. 웅장한 백제병원과 벽만 남은 남선창고터의 적벽돌에 세월의 흔적이 고스란히 묻어난다. 1920년대에 가장 화려하게 빛을 발했던 두 곳의 당시 상황이 머릿속에 그려지는 듯하다.\n\n\n\n외국인 선교사에 의해 최초로 세워진 초량교회를 찾아간다. 항일운동을 전개하고 신사참배에 반대하던 사람들의 집결지였다고 한다. 초량초등학교 담장을 따라가는 좁은 골목길에는 세월의 흔적이 가득 묻어난다. 저마다의 사진 속에 피난민의 희로애락이 비춰진다.\n\n\n\n도로도 없던 까꼬막 판자촌에 상수도가 있었을 리 없다. 168계단을 뛰어내려와 양동이에 물을 길어 끝이 없을 것 같은 계단을 다시 올라야 했겠지. 터만 남은 우물과 계단 옆 모노레일은 속절없이 흘러버린 세월의 무정함을 느끼게 한다.\n\n※ 168모노레일 정비로 인한 운행중지 2023.3.11.~완료시\n\n\n\n부산 출신의 천재시인 김민부를 기리는 김민부 전망대에서 잠시 숨을 돌리고 이바구공작소로 향한다. 이바구공작소는 초량 산복도로의 소소한 이야기를 전시하는 공간이다. 하늘과 맞닿을 듯 자리한 산복도로 마을에는 집집마다 사연이 많다. 거친 삶의 흔적을 담담히 전해주는 곳이다.\n\n\n\n평생 자신의 집 한 칸 마련하지 않고 가난한 피난민을 무료로 진료한 외과의사 장기려 박사. 의술이 아닌 인술을 베푼 그의 행적을 기리는 장기려더나눔센터에서 자발적 가난의 숭고함을 배운다. 시내버스가 다니는 산복도로를 만나는 지점 끝에 자리한 유치환우체통전망대에서 부산항을 조망하며 초량이바구길의 여정을 마감한다.\n\n세월이 느껴지는 골목마다 저마다의 이야기를 꽃피운다. 이야기를 따라가다 보니 이제 ‘이바구’가 이야기라는 걸, ‘까꼬막’이 오르기 힘든 고갯길이라는 걸 알게 된다.\n초량이바구길은 피난민들이 그러했듯, 멈추지 않고 계속되는 우리네 삶의 여정이다.\n\n\n"
//        },

// 모델 구조 샘플
// {
//    "getRecommendedKr": {
//    "header": {
//    "code": "00",
//    "message": "NORMAL_CODE"
//},
//    "item": [
//    {
//        "UC_SEQ": 58,
//        "MAIN_TITLE": "초량이바구길",
//        "GUGUN_NM": "동구",
class ItemListModel77(
    val getRecommendedKr: GetRecommendedKr
)

class GetRecommendedKr(
    val item: List<ItemModel77>?
)

class ItemModel77 {
    var MAIN_TITLE: String? = null
    var TITLE: String? = null
    var MAIN_IMG_NORMAL: String? = null
    var MAIN_IMG_THUMB: String? = null
    var GUGUN_NM: String? = null
}







