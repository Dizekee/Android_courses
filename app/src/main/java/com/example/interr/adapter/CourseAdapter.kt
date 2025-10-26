import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interr.R
import com.example.interr.model.Course
import com.example.interr.CourseActivity
import java.text.SimpleDateFormat
import java.util.*

class CourseAdapter(
    private val courses: List<Course>,
    private val onFavoriteClick: ((Course, Int) -> Unit)? = null,
    private val onDetailsClick: ((Course, Int) -> Unit)? = null
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val rate: TextView = itemView.findViewById(R.id.rate)
        val text: TextView = itemView.findViewById(R.id.text)
        val start: TextView = itemView.findViewById(R.id.start_date)
        val likeButton: ImageButton = itemView.findViewById(R.id.imageButton6)
        val detailsText: TextView = itemView.findViewById(R.id.more_detailed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]

        holder.title.text = course.title
        holder.price.text = "${course.price} ₽"
        holder.rate.text = course.rate.toString()
        holder.text.text = course.text
        holder.start.text = formatDate(course.startDate)

        updateBookmarkIcon(holder.likeButton, course.hasLike)

        holder.likeButton.setOnClickListener {

            course.hasLike = !course.hasLike

            updateBookmarkIcon(holder.likeButton, course.hasLike)

            onFavoriteClick?.invoke(course, position)
        }

        holder.detailsText.setOnClickListener {

            onDetailsClick?.invoke(course, position)

            val context = holder.itemView.context
            val intent = Intent(context, CourseActivity::class.java).apply {
                putExtra("COURSE_TITLE", course.title)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = courses.size

    private fun formatDate(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            inputDate
        }
    }

    private fun updateBookmarkIcon(button: ImageButton, isLiked: Boolean) {
        val bookmarkIcon = if (isLiked) {
            R.drawable.bookmarkk
        } else {
            R.drawable.bookmark_2
        }
        button.setImageResource(bookmarkIcon)
    }
}