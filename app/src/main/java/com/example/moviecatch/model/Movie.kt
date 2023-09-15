package com.example.moviecatch.model

data class Movie(
    val page:Int,
    val results:List<Result>,
    val total_pagers:Int,
    val total_results:Int
)
