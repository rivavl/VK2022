package com.marina.vk2022.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.marina.vk2022.R
import com.marina.vk2022.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        //если не авторизуемся, сразу переходим на основной экран
        binding.continueWithoutRegBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        //авторизация через вк
        binding.registerWithVkBtn.setOnClickListener {
            // TODO: 26.03.2022
        }
    }
}