package com.example.my_universe.MainAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_universe.ApiModel.ItemModel77
import com.example.my_universe.databinding.ItemRetrofit2Binding

class MyViewHolderRetrofit77 (val binding: ItemRetrofit2Binding)
    : RecyclerView.ViewHolder(binding.root)

// 리사이클러뷰 준비물 1) 어댑터 2) 뷰홀더 3) 목록 요소의 뷰 4) 데이터
// 매개변수 구성 1) context , 2) 데이터
// datas -> [모델1, 모델2, 모델3,....]
// 클래스의 주생성자 , 클래스명 뒤에 오는 소괄호 안에 내용. constructor 생략하고 많이 씀.
// val context : Context, val datas : List<UserModel>? ,
// 변수에 타입을 val, var 지정하면, 클래스 내에 전역으로 사용 가능.
// 그래서, 밑에 특정 함수 내부에서, datas를 쉽게 접근 및 사용이 가능.
//class MyAdapterRetrofit3(val context : Context, val datas : List<ItemModel2>?)
class MyAdapterRetrofit77(val context : Context, val datas : List<ItemModel77>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    // 틀 요소 할 때,
    // 변경 되는 부분은 목록 요소 뷰홀더 부분
    // 1) MyViewHolderRetrofit
    // 2) ItemRetrofitBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {
        // 목록 요소를 해당 어댑터에 적용한다.
        return MyViewHolderRetrofit77(
            ItemRetrofit2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    // 이코드 그대로 통으로 재사용.
    override fun getItemCount(): Int {
        // ? : 널 허용 연산자,  ?: 엘비스 연산자,
        // 해당 값이 있으면, 그 값을 사용하고 : datas.size 사용하고,
        // 이게 만약 널이라고 한다면, 엘비스 연산자 ?: 다음의 기본값을 사용하세요.
        return datas?.size ?: 0
    }

    // 실제로 해당 뷰에, 데이터를 연결하는 부분.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 내부에서 뷰 작업 쉽게 할려고, 뷰 홀더로 형변환 후, 바인딩 재할당.
        val binding = (holder as MyViewHolderRetrofit77).binding

        // datas 에 담겨진 모델을 하나씩 꺼내서 , 뷰에 데이터를 붙이는 작업.
        // 리스트의 각요소마다 하나씩 꺼내어, 임의의 user 변수에 담기.
        val item = datas?.get(position)
        // 뷰에 데이터 내용 붙이기. 1) 일반 텍스트 먼저 붙이기.
        binding.retrofitTitleView.text = item?.TITLE
        binding.retrofitContentView.text = item?.MAIN_TITLE


        // glide 통해서, 이미지 를 직접 가져와서 처리하는 부분.
        //방법2)
        Glide.with(context)
            //load 실제 URL 주소 직접 넣기.
            .load(item?.MAIN_IMG_NORMAL)
            .override(700,300)
            .into(binding.retrofitProfileImg2)
    }

}








