package com.androbrain.ui.fragments.choose_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androbrain.databinding.FragmentChooseProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseProfileFragment : Fragment() {
    private var _binding: FragmentChooseProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
