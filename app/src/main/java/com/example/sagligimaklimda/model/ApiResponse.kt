package com.example.sagligimaklimda.model

data class ApiResponse(
    val status: String,
    val message: String,
    val data: List<Pharmacy>
)