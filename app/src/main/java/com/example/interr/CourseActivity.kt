package com.example.interr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.interr.databinding.ActivityCourseBinding

@Suppress("DEPRECATION")
class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем переданное название курса из Intent
        val courseTitle = intent.getStringExtra("COURSE_TITLE")

        // Устанавливаем название курса в TextView
        binding.textView8.text = courseTitle ?: "Название курса"

        // Обработка кнопки "Назад"
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}