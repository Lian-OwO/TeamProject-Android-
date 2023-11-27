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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my_universe.MyApplication.Companion.db
import com.example.my_universe.databinding.ActivityBoardWriteBinding
import com.example.my_universe.model.BoardItem
import com.example.my_universe.utils.MyUtil
import com.google.firebase.Timestamp
import java.io.File
import java.util.UUID


class BoardWriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityBoardWriteBinding
    // 갤러리에서 선택된 , 파일의 위치
    // 초기화는 밑에서 하기.
    lateinit var filePath : String
    // xml 파일 내 동적인 이미지 뷰 선택하기 위한 변수
    lateinit var dynamicItemId : String
    var resourceId : Int = 0
    lateinit var dynamicItemImageView : ImageView
    // 다이얼로그 변수
    var selectedManu : String = "갤러리에서 선택하기"
    // 이미지 URL을 담을 배열
    val imageUrlArray = ArrayList<String?>()
    val imageFilepathArray = ArrayList<String?>()
    // 게시글 내용
    lateinit var title : String
    lateinit var subTitle : String
    lateinit var content : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyUtil.checkPermission(this)
        binding = ActivityBoardWriteBinding.inflate(layoutInflater)
        // 초기 뷰 설정
        binding.categoryTitle.headText.text = "제목"
        binding.categorySubTitle.headText.text = "부제목"

        val menus = arrayOf("갤러리에서 선택하기", "사진 지우기")

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(it.resultCode === android.app.Activity.RESULT_OK) {
                imageUrlArray.add(it.data?.data.toString())
                imageBinding(imageUrlArray)
                Log.d("kjh", "imageBinding 후!")
                val cursor = contentResolver.query(it.data?.data as Uri,
                    arrayOf<String>(MediaStore.Images.Media.DATA),null,
                    null,null);

                cursor?.moveToFirst().let {
                    filePath = cursor?.getString(0) as String
                    imageFilepathArray.add(filePath)
                    Log.d("kjh", "filePath는?")
                    Log.d("kjh", filePath)
                }
            }
        }

        binding.myImageView1.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            // 등록된 이미지가 없다면
            if(imageUrlArray.size==0) {
                imageLoad(requestLauncher)
            }
            // 등록된 이미지가 있다면
            else {
                builder.setTitle("사진 작업")
                builder.setItems(menus) { dialog: DialogInterface, which: Int ->
                    // 사용자가 선택한 항목에 대한 처리
                    selectedManu = menus[which]
                    if(selectedManu == "갤러리에서 선택하기") {
                        imageLoad(requestLauncher)
                    } else {
                        imageUrlArray.removeAt(0)
                        imageFilepathArray.removeAt(0)
                        imageBinding(imageUrlArray)
                    }
                }
                builder.show()
            }
        }
        binding.myImageView2.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            // 등록된 이미지가 없다면
            if(imageUrlArray.size<=1) {
                imageLoad(requestLauncher)
            }
            // 등록된 이미지가 있다면
            else {
                builder.setTitle("사진 작업")
                builder.setItems(menus) { dialog: DialogInterface, which: Int ->
                    // 사용자가 선택한 항목에 대한 처리
                    selectedManu = menus[which]
                    if(selectedManu == "갤러리에서 선택하기") {
                        imageLoad(requestLauncher)
                    } else {
                        imageUrlArray.removeAt(1)
                        imageFilepathArray.removeAt(1)
                        imageBinding(imageUrlArray)
                    }
                }
                builder.show()
            }
        }
        binding.myImageView3.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            // 등록된 이미지가 없다면
            if(imageUrlArray.size<=2) {
                imageLoad(requestLauncher)
            }
            // 등록된 이미지가 있다면
            else {
                builder.setTitle("사진 작업")
                builder.setItems(menus) { dialog: DialogInterface, which: Int ->
                    // 사용자가 선택한 항목에 대한 처리
                    selectedManu = menus[which]
                    if(selectedManu == "갤러리에서 선택하기") {
                        imageLoad(requestLauncher)
                    } else {
                        imageUrlArray.removeAt(2)
                        imageFilepathArray.removeAt(2)
                        imageBinding(imageUrlArray)
                    }
                }
                builder.show()
            }
        }
        binding.myImageView4.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            // 등록된 이미지가 없다면
            if(imageUrlArray.size<=3) {
                imageLoad(requestLauncher)
            }
            // 등록된 이미지가 있다면
            else {
                builder.setTitle("사진 작업")
                builder.setItems(menus) { dialog: DialogInterface, which: Int ->
                    // 사용자가 선택한 항목에 대한 처리
                    selectedManu = menus[which]
                    if(selectedManu == "갤러리에서 선택하기") {
                        imageLoad(requestLauncher)
                    } else {
                        imageUrlArray.removeAt(3)
                        imageFilepathArray.removeAt(3)
                        imageBinding(imageUrlArray)
                    }
                }
                builder.show()
            }
        }

        binding.btnSubmit.setOnClickListener {
            title = binding.categoryTitle.textEdit.getText().toString()
            subTitle = binding.categorySubTitle.textEdit.getText().toString()
            content = binding.textContent.getText().toString()
            val board = BoardItem(
                title = binding.categoryTitle.textEdit.text.toString(),
                subTitle = binding.categorySubTitle.textEdit.text.toString(),
                content = binding.textContent.text.toString(),
                timestamp = Timestamp.now(),
                images = mutableMapOf()
            )

            // 스토리지 접근 도구 ,인스턴스
            val storage = MyApplication.storage
            // 스토리지에 저장할 인스턴스
            val storageRef = storage.reference
            Log.d("kjh", "imgRef 후!")
            // 파일 불러오기. 갤러리에서 사진을 선택 했고, 또한, 해당 위치에 접근해서,
            // 파일도 불러오기 가능함.
            for ((index, filePath) in imageFilepathArray.withIndex()) {
                val dynamicKey = "img${index + 1}"
                var file : Uri = Uri.fromFile(File(filePath))
                val uuid = UUID.randomUUID().toString()
                // 이미지 저장될 위치 및 파일명
                val imgRef = storageRef.child("AndroidImg/${uuid}.jpg")
                board.addImage(dynamicKey,"AndroidImg/${uuid}.jpg")
                imgRef.putFile(file)
                    // 업로드 후, 수행할 콜백 함수 정의. 실패했을 경우 콜백함수 정의
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful) {
                            Log.d("kjh", "업로드 성공!")
                            val boardCollection = db.collection("boardList")
                            boardCollection.add(board)
                                .addOnSuccessListener { documentReference ->
                                    // 성공 시 처리
                                    Log.d("게시글 업로드", "DocumentSnapshot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener { e ->
                                    // 실패 시 처리
                                    Log.w("게시글 업로드", "Error adding document", e)
                                }
                            finish()
                        } else {
                            // 업로드 실패
                            Log.d("kjh", "업로드 실패!")
                            Toast.makeText(this, "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }


        setContentView(binding.root)
    }

    private fun imageLoad(requestLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
        )
        requestLauncher.launch(intent)
    }
    private fun imageBinding(imageUrlArray : ArrayList<String?>) {
        if(imageUrlArray.size != 0) {
            for((index, imageUrl) in imageUrlArray.withIndex()) {
                dynamicItemId = "myImageView" + (index+1).toString()
                resourceId = resources.getIdentifier(dynamicItemId, "id", packageName)
                dynamicItemImageView = binding.root.findViewById<ImageView>(resourceId)
                Glide
                    .with(getApplicationContext())
                    .load(imageUrl)
                    .apply(RequestOptions().override(250,250))
                    .centerCrop()
                    .into(dynamicItemImageView)
            }
        }
        if(imageUrlArray.size != 4) {
            for(i in imageUrlArray.size..3) {
                dynamicItemId = "myImageView" + (i+1).toString()
                resourceId = resources.getIdentifier(dynamicItemId, "id", packageName)
                dynamicItemImageView = binding.root.findViewById<ImageView>(resourceId)
                dynamicItemImageView.setImageResource(R.drawable.addimage)
            }
        }
    }
}