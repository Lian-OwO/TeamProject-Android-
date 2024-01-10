import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.my_universe.R
import com.my_universe.model.ReviewModel

// ReviewAdapter.kt

class ReviewAdapter(private val dataList: List<ReviewModel>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewPager: ViewPager2 = itemView.findViewById(R.id.tapFragSliderviewPager2)
        val titleTextView: TextView = itemView.findViewById(R.id.tapFragSliderviewPager2)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        // 뷰페이저 어댑터 설정
        val pagerAdapter = ReviewPagerAdapter(currentItem.imageUrl)
        holder.viewPager.adapter = pagerAdapter

        // 나머지 항목들 설정
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
