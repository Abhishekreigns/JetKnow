package com.reignscanary.jetknow.backend


data class Contributions(
    val id: String? = null,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val name: String = "",
    val number: String ="",
    val address: String = "",
    val searchText: String =""
)