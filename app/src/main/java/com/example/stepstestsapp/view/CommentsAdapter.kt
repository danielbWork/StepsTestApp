package com.example.stepstestsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stepstestsapp.R
import com.example.stepstestsapp.databinding.CommentViewHolderBinding
import com.example.stepstestsapp.model.Comment


/**
 * The adapter used to display the comments list
 */
class CommentsAdapter() : PagingDataAdapter<Comment, CommentViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding: CommentViewHolderBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.comment_view_holder, parent, false
        )

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = getItem(position)

        if (comment != null) {
            holder.binding.comment = comment
        }
    }

    // region DiffUtil

    /**
     * Used for the paging list
     */
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }
        }
    }

    // endregion

}

// region CommentViewHolder

/**
 * The view holder user to display a single comment
 */
class CommentViewHolder(val binding: CommentViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

}

// endregion
