import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my_universe.R

// ReviewPagerAdapter.kt

class ReviewPagerAdapter(private val imageList: List<String>) : RecyclerView.Adapter<ReviewPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResId = imageList[position]

        // 이미지 로딩 라이브러리(Glide 등)를 사용하여 이미지 설정
        Glide.with(holder.itemView.context)
            .load(imageResId)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
