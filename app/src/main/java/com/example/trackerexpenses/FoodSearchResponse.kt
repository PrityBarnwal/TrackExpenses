package com.example.trackerexpenses


data class FoodSearchResponse(
    val products: List<Product>
)

data class Product(
    val product_name: String?,
    val quantity: String?,
    val nutrition_grades: String?,
    val image_url: String?
)
