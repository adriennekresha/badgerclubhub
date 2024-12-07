package com.cs407.badgerclubhub
import java.io.Serializable



data class Club (
    //val id: String, not relevant??
    val name: String,
    val description: String,
    //val categoryIds: List<String>, not relevant?/
    val categoryNames: List<String>,
    //val status: String,: not relevant??
    //val visibility: String: not relevant??
): Serializable

