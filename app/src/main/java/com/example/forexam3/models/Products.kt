package com.example.forexam3.models

import kotlinx.serialization.Serializable

@Serializable
data class Products(
    val id: String?,
    val article: Int,
    val title: String,
    val grams: Int,
    val sing_18: Boolean,
    val description: String,
    val price: Int,
    val image: String,
    val rating: Float,
    val country_id: String?,
    val categories_id: String?,
    val prodStatus_id: String?,
    val countRev: Int,

    var country: countryProd? = null,
    var category: categories? = null
)