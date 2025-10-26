package com.example.interr.model

class Course {
    var id: Int
    var price: Int
    var title: String
    var text: String
    var startDate: String
    var rate: Double
    var hasLike: Boolean

    constructor(
        id: Int,
        text: String,
        price: Int,
        title: String,
        startDate: String,
        rate: Double,
        hasLike: Boolean
    ) {
        this.id = id
        this.text = text
        this.price = price
        this.title = title
        this.startDate = startDate
        this.rate = rate
        this.hasLike = hasLike
    }
}