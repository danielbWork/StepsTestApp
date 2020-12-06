package com.example.stepstestsapp.server

import androidx.paging.PagingSource
import com.example.stepstestsapp.model.Comment
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.min

/**
 *  The logic for the paging library, that actually loads the info
 */
class CommentsDataSource(private val minId: Int, private val maxId: Int) :
        PagingSource<Int, Comment>() {

    private val commentsService = CommentsService.getService()

    /**
     * Loads the comments from the server.
     * @param params The params for the load
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {

        val startId = params.key ?: minId
        val endId = min(maxId, startId + params.loadSize)

        val comments = ArrayList<Comment>(endId - startId)

        return try {

            // Attempts to get all of the comments
            for (id in startId..endId) {
                comments.add(commentsService.getComment(id))
            }

            LoadResult.Page(
                    data = comments,
                    prevKey = if (startId == minId) null else startId - params.loadSize,
                    nextKey = if (endId == maxId) null else endId + 1

            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

}