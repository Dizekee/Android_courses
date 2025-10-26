package com.example.interr.ui.dashboard

import CourseAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interr.CourseActivity
import com.example.interr.R
import com.example.interr.SharedViewModel
import com.example.interr.databinding.FragmentDashboardBinding
import com.example.interr.model.Course

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализируем SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Настраиваем RecyclerView
        setupRecyclerView()

        // Наблюдаем за изменениями в списке избранных курсов
        sharedViewModel.favoriteCourses.observe(viewLifecycleOwner) { favorites ->
            updateFavoritesList(favorites)
        }
    }

    private fun setupRecyclerView() {
        adapter = CourseAdapter(
            courses = emptyList(),
            onFavoriteClick = { course, position ->
                // Удаляем курс из избранного
                sharedViewModel.removeFavoriteCourse(course.id)
            },
            onDetailsClick = { course, position ->
                // Обработка клика на детали (можно оставить пустым или реализовать переход)
            }
        )

        binding.courseRecucler.layoutManager = LinearLayoutManager(requireContext())
        binding.courseRecucler.adapter = adapter
    }

    private fun updateFavoritesList(favorites: List<Course>) {
        adapter = CourseAdapter(
            courses = favorites,
            onFavoriteClick = { course, position ->
                // Удаляем курс из избранного
                sharedViewModel.removeFavoriteCourse(course.id)
            },
            onDetailsClick = { course, position ->
                // Обработка клика на детали
                // Можно добавить переход на CourseActivity как в HomeFragment
                val intent = Intent(requireContext(), CourseActivity::class.java).apply {
                    putExtra("COURSE_TITLE", course.title)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        )
        binding.courseRecucler.adapter = adapter

        // Показываем/скрываем сообщение если список пуст
        if (favorites.isEmpty()) {
            showEmptyState()
        } else {
            hideEmptyState()
        }
    }

    private fun showEmptyState() {
        // Создаем TextView для пустого состояния, если его нет в layout
        val emptyText = TextView(requireContext()).apply {
            text = "В избранном пока ничего нет"
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.darker_gray))
        }
        binding.root.addView(emptyText)
    }

    private fun hideEmptyState() {
        // Убираем TextView пустого состояния
        binding.root.findViewById<TextView>(R.id.empty_state_text)?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}