package com.example.stepstestsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stepstestsapp.R
import com.example.stepstestsapp.databinding.CommentsFooterLayoutBinding

/**
 * Used as footer in comment list to display info to user about state
 */
class CommentsLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.comments_footer_layout, parent, false)
        )
    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.binding.loadStateProgress
        val txtErrorMessage = holder.binding.loadStateErrorMessage

        // Sets the footer's ui
        txtErrorMessage.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        progress.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

    }

}

class LoadStateViewHolder(val binding: CommentsFooterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)