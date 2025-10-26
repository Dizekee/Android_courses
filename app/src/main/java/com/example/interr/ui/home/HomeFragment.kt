package com.example.interr.ui.home

import CourseAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interr.databinding.FragmentHomeBinding
import com.example.interr.model.Course
import com.google.gson.Gson
import java.io.InputStreamReader

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCoursesFromJson()
    }

    private fun setupRecyclerView() {
        courseAdapter = CourseAdapter(emptyList())
        binding.courseRecucler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
    }

    private fun loadCoursesFromJson() {
        try {
            val inputStream = requireContext().assets.open("courses.json")
            val jsonString = InputStreamReader(inputStream).use { it.readText() }

            val gson = Gson()
            val courseResponse = gson.fromJson(jsonString, CourseResponse::class.java)

            // Преобразуем JSON-данные в объекты Course
            val courses = courseResponse.courses.map { jsonCourse ->
                Course(
                    id = jsonCourse.id,
                    title = jsonCourse.title,
                    text = jsonCourse.text,
                    price = jsonCourse.price.replace("\\s".toRegex(), "").toIntOrNull() ?: 0,
                    startDate = jsonCourse.startDate,
                    rate = jsonCourse.rate.toDoubleOrNull() ?: 0.0,
                    hasLike = jsonCourse.hasLike
                )
            }

            courseAdapter = CourseAdapter(courses)
            binding.courseRecucler.adapter = courseAdapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
data class CourseResponse(
    val courses: List<JsonCourse>
)

data class JsonCourse(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)