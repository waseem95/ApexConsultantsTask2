package com.task.apexConsultants.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import apexConsultants.R
import apexConsultants.databinding.FragmentGuestBinding
import com.google.android.material.snackbar.Snackbar
import com.task.apexConsultants.MVVM.MainViewModel

class GuestFragment : Fragment() {
    private lateinit var binding: FragmentGuestBinding
    private val viewModel: MainViewModel by viewModels()
    private var message: String? = ""
    private var reservationNeeded: Boolean? = false
    private lateinit var postsAdapter: GuestsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.populateList(requireContext())

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupRecyclerView()
    }

    private fun initViews() {
        viewModel.buttonState.observe(viewLifecycleOwner) { state ->
            changeButtonState(state)
        }

        binding.continueButton.setOnClickListener {
            if (viewModel.buttonState.value == true) {
                if (reservationNeeded == true) {
                    Snackbar.make(binding.container, message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                findNavController().navigate(
                    GuestFragmentDirections.actionGuestFragmentToResultFragment(
                        message ?: ""
                    )
                )
            }
        }
    }

    private fun validate() {

        val haveSeat = viewModel.guestsList.filter {
            it.type == "have_seat" && it.checked == true
        }

        val needSeat = viewModel.guestsList.filter {
            it.type == "need_seat"
        }

        val filteredHaveSeatList = haveSeat.filter {
            it.checked == true
        }
        val filteredNeedSeatList = needSeat.filter {
            it.checked == true
        }


        if (filteredHaveSeatList.isEmpty() && filteredNeedSeatList.isEmpty()) {
            reservationNeeded = false
            viewModel.updateButtonState(false)
        } else {
            if (haveSeat.isEmpty()) {
                reservationNeeded = true
                message = getString(R.string.select_reservation)
            } else {
                message = if (filteredNeedSeatList.isNotEmpty()) {
                    getString(R.string.conflicts_found)
                } else {
                    getString(R.string.success)
                }
                reservationNeeded = false
            }
            viewModel.updateButtonState(true)
        }
    }

    private fun changeButtonState(enable: Boolean? = false) {
        if (enable == true)
            binding.continueButton.alpha =
                1.0f
        else
            binding.continueButton.alpha = 0.5f
    }

    private fun setupRecyclerView() = binding.recyclerview1.apply {
        postsAdapter = GuestsAdapter(viewModel.guestsList) {
            validate()
        }
        adapter = postsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}