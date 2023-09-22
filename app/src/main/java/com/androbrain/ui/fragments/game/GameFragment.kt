package com.androbrain.ui.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androbrain.R
import com.androbrain.core.sound.MediaPlayerSoundManager
import com.androbrain.core.sound.SoundManager
import com.androbrain.core.utils.APP_MARKET_LINK
import com.androbrain.core.utils.APP_PAGE_LINK
import com.androbrain.core.utils.openLink
import com.androbrain.core.utils.showInAppReview
import com.androbrain.databinding.FragmentGameBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val MIN_SWIPE_INTERVAL = 400L

@AndroidEntryPoint
class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    internal val viewModel: GameViewModel by viewModels()
    private var lastSwipeTime: Long? = null
    private val adapter by lazy {
        GameCardAdapter(cards = emptyList())
    }
    private val layoutManager get() = binding.cardStack.layoutManager as CardStackLayoutManager
    private val soundManager: SoundManager = MediaPlayerSoundManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater)
        setupViews()
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupViews() = with(binding) {
        root.applyInsetter {
            type(statusBars = true, navigationBars = true) {
                margin()
            }
        }
        val layoutManager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) = Unit
            override fun onCardSwiped(direction: Direction?) {
                viewModel.nextQuestion()
                soundManager.playConcurrentSound(
                    context = requireContext(),
                    soundId = R.raw.sound_card_flip
                )
            }

            override fun onCardRewound() = Unit
            override fun onCardCanceled() = Unit
            override fun onCardAppeared(view: View?, position: Int) = Unit
            override fun onCardDisappeared(view: View?, position: Int) = Unit
        })
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        layoutManager.setCanScrollHorizontal(true)
        layoutManager.setCanScrollVertical(false)
        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setVisibleCount(2)
        cardStack.layoutManager = layoutManager
        cardStack.adapter = adapter
    }

    private fun setupActions() = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        toolbar.setOnMenuItemClickListener {
            Toast.makeText(requireContext(), R.string.rate_to_help_us, Toast.LENGTH_LONG).show()
            requireContext().openLink(
                link = APP_MARKET_LINK,
                failLink = APP_PAGE_LINK
            )
            true
        }

        buttonNext.setOnClickListener {
            swipe(Direction.HORIZONTAL.random())
        }
    }

    private fun swipe(direction: Direction) {
        val swipeTime = System.currentTimeMillis()
        val previousSwipeTime = lastSwipeTime
        if (previousSwipeTime == null || swipeTime - previousSwipeTime > MIN_SWIPE_INTERVAL) {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            binding.cardStack.swipe()
            lastSwipeTime = swipeTime
        }
    }

    private fun setupObservers() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach { state ->
                    layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
                    if (state.showInAppReview) {
                        requireActivity().showInAppReview()
                        viewModel.inAppReviewShown()
                    }
                    state.game?.let { game ->
                        if (game.questionsIndex >= game.questions.size) {
                            //                            TODO finish the game with dialog
                        }
                        progressIndicator.setProgressCompat(game.questionsIndex, true)
                        progressIndicator.max = game.questions.size
                        adapter.setCards(
                            cards = game.questions.takeLast(game.questions.size - game.questionsIndex),
                            changedItem = game.questionsIndex - 1,
                        )
                    }
                }.launchIn(this)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
