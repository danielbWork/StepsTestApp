package com.example.stepstestsapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepstestsapp.R
import com.example.stepstestsapp.databinding.FragmentCommentsBinding
import com.example.stepstestsapp.viewmodel.CommentsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

const val MIN_LOADING_TIME = 3000L

/**
 * The fragment used to load and display the comments to the user
 */
class CommentsFragment : Fragment() {

    // region Data Members

    lateinit var binding: FragmentCommentsBinding

    lateinit var viewModel: CommentsViewModel

    var loadJob: Job? = null

    lateinit var adapter: CommentsAdapter

    // endregion

    // region Fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)
        binding.viewModel = viewModel

        adapter = CommentsAdapter()

        // Set UI info for the list
        binding.list.let {
            it.adapter = adapter.withLoadStateFooter(CommentsLoadStateAdapter())
            it.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        handleBackPress()

        loadComments()

        // Inflate the layout for this fragment
        return binding.root
    }

    // endregion

    // region Private Methods

    /**
     * Enables going back to the main screen and canceling any running request
     */
    private fun handleBackPress() {

        requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Stops any running load tasks
                        loadJob?.cancel()
                        // Goes back in the navigation
                        findNavController().popBackStack()
                    }

                })
    }

    /**
     * Handles everything relating to loading the comments
     */
    private fun loadComments() {

        loadJob = lifecycleScope.launch {

            // This makes the adapter receive updates from the paging library
            viewModel.loadComments().observe(viewLifecycleOwner, {

                adapter.submitData(lifecycle, it)

            })

            // Waits for the min time
            delay(MIN_LOADING_TIME)

            // Waits until the adapter stops initial load
            adapter.addLoadStateListener(object : (CombinedLoadStates) -> Unit {
                override fun invoke(states: CombinedLoadStates) {

                    // Once refresh enters not loading the initial load finished
                    if (states.refresh is LoadState.NotLoading) {
                        viewModel.inInitialLoad.value = false
                        adapter.removeLoadStateListener(this)
                    }
                }

            })

        }


    }


    // endregion

}