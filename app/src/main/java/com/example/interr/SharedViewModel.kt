package com.example.interr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interr.model.Course

class SharedViewModel : ViewModel() {
    private val _favoriteCourses = MutableLiveData<MutableList<Course>>(mutableListOf())
    val favoriteCourses: LiveData<MutableList<Course>> = _favoriteCourses

    fun addFavoriteCourse(course: Course) {
        val current = _favoriteCourses.value ?: mutableListOf()
        if (current.none { it.id == course.id }) {
            current.add(course)
            _favoriteCourses.value = current
        }
    }

    fun removeFavoriteCourse(courseId: Int) {
        val current = _favoriteCourses.value ?: mutableListOf()
        current.removeAll { it.id == courseId }
        _favoriteCourses.value = current
    }

    fun isCourseFavorite(courseId: Int): Boolean {
        return _favoriteCourses.value?.any { it.id == courseId } ?: false
    }
}