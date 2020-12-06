package com.example.stepstestsapp.server

import com.example.stepstestsapp.model.Comment
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The service connecting between our code and the server side
 */
interface CommentsService {

    /**
     * Sends a get request for the comment with the given id
     * @param id The id of the comment the user is requesting
     */
    @GET("/comments/{id}")
    suspend fun getComment(@Path("id") id: Int): Comment

    /**
     * Used to initiate the service easily
     */
    companion object {
        fun getService(): CommentsService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CommentsService::class.java)
        }
    }

}