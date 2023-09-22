package com.androbrain.ui.fragments.choose_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androbrain.core.navigation.navigateSafely
import com.androbrain.databinding.FragmentChooseProfileBinding
import com.androbrain.ui.fragments.ChooseProfileController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseProfileFragment : Fragment() {
    private var _binding: FragmentChooseProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChooseProfileViewModel by viewModels()
    private val controller by lazy {
        ChooseProfileController(
            onProfileClick = { game ->
                findNavController().navigateSafely(
                    ChooseProfileFragmentDirections.actionChooseProfileFragmentToGameFragment(game)
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseProfileBinding.inflate(inflater)
        setupUi()
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupUi() = with(binding) {
        root.applyInsetter {
            type(navigationBars = true) {
                margin()
            }
        }
        toolbar.applyInsetter {
            type(statusBars = true) {
                padding()
            }
        }
        recycler.setController(controller)
    }

    private fun setupActions() = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach { state ->
                    controller.profiles = state.games
                }.launchIn(this)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
