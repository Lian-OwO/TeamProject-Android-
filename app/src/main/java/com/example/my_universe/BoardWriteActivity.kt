package com.example.my_universe

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my_universe.databinding.ActivityBoardWriteBinding
import com.example.my_universe.model.BoardItem
import com.example.my_universe.model.ImageState


class BoardWriteActivity : AppCompatActivity() {
    // 갤러리에서 선택된 , 파일의 위치
    // 초기화는 밑에서 하기.
    lateinit var filePath : String
    var imageState = ImageState(0, 0, 0, null)
    lateinit var dynamicItemId : String
    var resourceId : Int = 0
    lateinit var dynamicItemImageView : ImageView
    var selectedManu : String = "갤러리에서 선택하기"
    // 이미지 URL을 담을 배열
    val imageUrlArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var binding : ActivityBoardWriteBinding
        val defaultImage = R.drawable.addimage
        binding = ActivityBoardWriteBinding.inflate(layoutInflater)
        // 초기 뷰 설정
        binding.categoryTitle.headText.text = "제목"
        binding.categorySubTitle.headText.text = "부제목"

        val menus = arrayOf("갤러리에서 선택하기", "사진 지우기")

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(it.resultCode === android.app.Activity.RESULT_OK) {
                dynamicItemImageView = binding.root.findViewById<ImageView>(resourceId)
                Glide
                    .with(getApplicationContext())
                    .load(it.data?.data)
                    .apply(RequestOptions().override(250,250))
                    .centerCrop()
                    .into(dynamicItemImageView)

                imageUrlArray.add(it.data?.data.toString())

                val cursor = contentResolver.query(it.data?.data as Uri,
                    arrayOf<String>(MediaStore.Images.Media.DATA),null,
                    null,null);

                cursor?.moveToFirst().let {
                    filePath = cursor?.getString(0) as String
                }
            }
        }

        binding.myImageView1.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            imageState.imageSelectedNum = imageUrlArray.size + 1
            imageState.imageNumState++
            dynamicItemId = "myImageView" + imageState.imageSelectedNum
            resourceId = resources.getIdentifier(dynamicItemId, "id", packageName)
            if(imageUrlArray.size==0) {
                // 이미지가 기본 이미지라면
                imageLoad(imageState, imageUrlArray, requestLauncher)
                imageState.isAddImage=true
            } else {
                builder.setTitle("사진 작업")
                builder.setItems(menus) { dialog: DialogInterface, which: Int ->
                    // 사용자가 선택한 항목에 대한 처리
                    selectedManu = menus[which]

                    if(selectedManu == "갤러리에서 선택하기") {
                        imageLoad(imageState, imageUrlArray, requestLauncher)
                        imageState.isAddImage=true
                    } else {
                        binding.myImageView1.setImageResource(R.drawable.addimage)
                        imageState.imageNumState--
                        imageState.isAddImage=false
                    }
                }
                builder.show()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.categoryTitle.textEdit.text.toString()
            val subTitle = binding.categorySubTitle.textEdit.text.toString()
            val content = binding.textContent.text.toString()

            val boardItem : BoardItem = BoardItem(title, subTitle, content)

            Log.d("kjh", boardItem.title)
            Log.d("kjh", boardItem.subTitle)
            Log.d("kjh", boardItem.content)
        }
        binding.btnCancel.setOnClickListener {

        }


        setContentView(binding.root)
    }

    private fun imageLoad(imageState : ImageState, imageList : ArrayList<String>, requestLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
        )
        requestLauncher.launch(intent)
    }
}