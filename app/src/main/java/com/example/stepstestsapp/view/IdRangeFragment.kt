package com.example.stepstestsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.stepstestsapp.R
import com.example.stepstestsapp.databinding.FragmentIdRangeBinding
import com.example.stepstestsapp.viewmodel.CommentsViewModel


const val EMPTY_ERROR_MESSAGE = "Id Value Can't be Empty"
const val MIN_BIGGER_THEN_MAX_ERROR_MESSAGE = "Min id can't be larger then max id"
const val ZERO_ID_ERROR_MESSAGE = "Id can't be zero"
const val MAX_ID = 500
const val OVER_ID_LIMIT_ERROR_MESSAGE = "Id can't be above $MAX_ID"

/**
 * The Fragment used to receive the bounds from the user.
 */
class IdRangeFragment : Fragment(), Observer<String> {

    // region Data Members

    lateinit var binding: FragmentIdRangeBinding

    lateinit var viewModel: CommentsViewModel

    // endregion

    // region Fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_id_range, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        addObservers()

        binding.loadIds.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_numberRangeFragment_to_commentsFragment))

        // Inflate the layout for this fragment
        return binding.root
    }

    // endregion

    // region Private Methods


    /**
     * Used to set the appropriate observers to the view model
     */
    private fun addObservers() {
        viewModel.minId.observe(viewLifecycleOwner, this)
        viewModel.maxId.observe(viewLifecycleOwner, this)
    }

    // endregion

    // region Observer

    /**
     * Used to make sure that we have valid values for the min and max id bounds
     */
    override fun onChanged(t: String?) {

        val minId: String = viewModel.minId.value ?: ""
        val maxId: String = viewModel.maxId.value ?: ""

        binding.loadIds.isEnabled = false

        // Validates the id values
        when {
            minId.isEmpty() -> {
                binding.minIdEt.error = EMPTY_ERROR_MESSAGE
            }
            minId.toInt() == 0 -> {
                binding.minIdEt.error = ZERO_ID_ERROR_MESSAGE
            }
            minId.toInt() > MAX_ID -> {
                binding.minIdEt.error = OVER_ID_LIMIT_ERROR_MESSAGE
            }
            maxId.isEmpty() -> {
                binding.maxIdEt.error = EMPTY_ERROR_MESSAGE
            }
            maxId.toInt() == 0 -> {
                binding.minIdEt.error = ZERO_ID_ERROR_MESSAGE
            }
            maxId.toInt() > MAX_ID -> {
                binding.maxIdEt.error = OVER_ID_LIMIT_ERROR_MESSAGE
            }

            minId.toInt() > maxId.toInt() -> {
                binding.minIdEt.error = MIN_BIGGER_THEN_MAX_ERROR_MESSAGE
            }
            else -> {
                binding.loadIds.isEnabled = true
                binding.minIdEt.error = null
                binding.maxIdEt.error = null
            }
        }


    }

    // endregion


}