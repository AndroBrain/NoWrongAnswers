package com.androbrain.ui.fragments.menu

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androbrain.R
import com.androbrain.core.anim.scaleUpAndDown
import com.androbrain.core.navigation.navigateSafely
import com.androbrain.core.utils.APP_MARKET_LINK
import com.androbrain.core.utils.APP_PAGE_LINK
import com.androbrain.core.utils.DEV_PAGE_LINK
import com.androbrain.core.utils.openLink
import com.androbrain.core.utils.shareLink
import com.androbrain.data.settings.lightning.LightningMode
import com.androbrain.databinding.FragmentMenuBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val DARK_MODE_INDEX = 0
private const val LIGHT_MODE_INDEX = 1

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater)
        setupUi()
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupUi() = with(binding) {
        root.applyInsetter {
            type(statusBars = true, navigationBars = true) {
                margin()
            }
        }
    }

    private fun setupActions() = with(binding) {
        buttonPlay.setOnClickListener { findNavController().navigateSafely(MenuFragmentDirections.actionMenuFragmentToGameFragment()) }

        buttonLoad.setOnClickListener {
//            val games = viewModel.state.value.games
            val games = arrayOf("x")
            if (games.size == 1) {
                viewModel.noteContinue()
                findNavController().navigateSafely(
                    MenuFragmentDirections.actionMenuFragmentToGameFragment(
//                        GameArgs(games.first().id)
                    )
                )
            } else {
                findNavController().navigateSafely(MenuFragmentDirections.actionMenuFragmentToChooseProfileFragment())
            }
        }

        chipRate.setOnClickListener {
            context?.openLink(
                link = APP_MARKET_LINK,
                failLink = APP_PAGE_LINK
            )
        }

        chipShare.setOnClickListener { requireContext().shareLink(APP_PAGE_LINK) }

        chipDayNight.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.choose_mode)
                .setItems(R.array.lightning_modes) { _: DialogInterface, selectedIndex: Int ->
                    when (selectedIndex) {
                        DARK_MODE_INDEX -> viewModel.setLightningMode(LightningMode.DARK)
                        LIGHT_MODE_INDEX -> viewModel.setLightningMode(LightningMode.LIGHT)
                        else -> viewModel.setLightningMode(LightningMode.SYSTEM)
                    }
                }
                .show()
        }

        chipMoreGames.setOnClickListener {
            context?.openLink(link = DEV_PAGE_LINK)
        }
    }

    private fun setupObservers() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach { state ->
                    if (state.canContinue) {
                        buttonLoad.isVisible = true
                        buttonPlay.clearAnimation()
                        _binding?.buttonLoad.scaleUpAndDown()
                    } else {
                        buttonLoad.clearAnimation()
                        buttonLoad.isVisible = false
                        _binding?.buttonPlay.scaleUpAndDown()
                    }

                    when (state.lightningMode) {
                        LightningMode.DARK -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            setChipLightMode()
                        }

                        LightningMode.LIGHT -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            setChipDarkMode()
                        }

                        else -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_NO -> setChipDarkMode()
                                Configuration.UI_MODE_NIGHT_YES -> setChipLightMode()
                            }
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    private fun setChipDarkMode() = with(binding) {
        chipDayNight.setText(R.string.dark_mode)
        chipDayNight.setChipIconResource(R.drawable.ic_dark_mode)
    }

    private fun setChipLightMode() = with(binding) {
        chipDayNight.setText(R.string.light_mode)
        chipDayNight.setChipIconResource(R.drawable.ic_light_mode)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}