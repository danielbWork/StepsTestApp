package com.example.stepstestsapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.stepstestsapp.model.Comment
import com.example.stepstestsapp.repository.CommentsRepository

const val DEFAULT_VALUE: String = "1"

/**
 * ViewModel used to connect everything relating with the comments to the ui
 */
class CommentsViewModel : ViewModel() {

    // region Data Members

    var minId = MutableLiveData(DEFAULT_VALUE)
    var maxId = MutableLiveData(DEFAULT_VALUE)

    var comments: LiveData<PagingData<Comment>>? = null

    val inInitialLoad = MutableLiveData(true)

    // endregion

    // region Public Methods

    /**
     * Starts loading the comments inside the id range
     */
    fun loadComments(): LiveData<PagingData<Comment>> {

        inInitialLoad.value = true

        // Loads the comments
        val newComments = CommentsRepository.getCommentsResultStream(
                minId.value!!.toInt(),
                maxId.value!!.toInt())

        comments = newComments

        return newComments
    }

    // endregion

}