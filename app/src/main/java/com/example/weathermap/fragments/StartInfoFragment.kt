package com.example.weathermap.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weathermap.MainActivity
import com.example.weathermap.R
import com.example.weathermap.databinding.FragmentStartInfoBinding

class StartInfoFragment : Fragment() {

    private lateinit var binding: FragmentStartInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = this.getText(R.string.instruction)
        binding = FragmentStartInfoBinding.inflate(inflater, container, false)

        binding.btStartApp.setOnClickListener {
            findNavController().navigate(R.id.action_startInfoFragment_to_todayTempFragment)
        }

        return binding.root
    }

}