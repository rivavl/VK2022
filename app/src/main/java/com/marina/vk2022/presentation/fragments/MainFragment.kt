package com.marina.vk2022.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marina.vk2022.R
import com.marina.vk2022.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setupButtonListeners()
    }

    private fun setupButtonListeners() {

        //начинаем запись
        binding.startRecordImageBtn.setOnClickListener {
            binding.startRecordImageBtn.visibility = View.GONE
            binding.stopRecordImageBtn.visibility = View.VISIBLE
            startRecord()
        }

        //заканчиваем запись
        binding.stopRecordImageBtn.setOnClickListener {
            binding.startRecordImageBtn.visibility = View.VISIBLE
            binding.stopRecordImageBtn.visibility = View.GONE
            stopRecord()
        }
    }

    private fun startRecord() {

    }

    private fun stopRecord() {

    }
}