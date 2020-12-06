package com.example.stepstestsapp.model

import com.google.gson.annotations.SerializedName

/**
 * The representation of Comments in the code
 */
data class Comment(
        val postId: Int,
        val id: Int,
        val name: String,
        val email: String,
        val body: String
)