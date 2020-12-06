package com.example.stepstestsapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.stepstestsapp.model.Comment
import com.example.stepstestsapp.server.CommentsDataSource

const val INITIAL_LOAD_SIZE = 12

/**
 * This decides how many comments to load per load, the bigger the number the more comments will be
 * loaded, however the load will take longer since it requests more comments
 */
const val PAGE_SIZE = 5

/**
 * This decides when to start loading the next bunch of comments, this tells the paging library
 * to load the comments in advance when the user reaches in the ui the item with the
 * id = $LastLoadedItemId - PREFETCH_DISTANCE .
 * This means the larger the value the earlier we load the comments
 */
const val PREFETCH_DISTANCE = 5

/**
 * Repository used as the location to get information relating to the comments
 */
object CommentsRepository {

    /**
     * Loads the comments from the server async
     *
     * @param minId The id of the first comment we want to load
     * @param maxId The id of the last comment we want to load
     *
     * @return The paging data holding all of the information
     */
    fun getCommentsResultStream(minId: Int, maxId: Int): LiveData<PagingData<Comment>> {

        return Pager(
                config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        initialLoadSize = INITIAL_LOAD_SIZE,
                        prefetchDistance = PREFETCH_DISTANCE

                ),
                pagingSourceFactory = {
                    CommentsDataSource(minId, maxId)
                }
        ).liveData
    }

}