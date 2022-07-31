package com.task.apexConsultants.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import apexConsultants.R
import apexConsultants.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private val args by navArgs<ResultFragmentArgs>()
    private lateinit var binding: FragmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messageTv.text = args.message
        if (args.message == getString(R.string.conflicts_found))
            binding.doneButton.text = "Go Back"
        binding.btnBack.setOnClickListener {
            goBack()
        }
        binding.doneButton.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}