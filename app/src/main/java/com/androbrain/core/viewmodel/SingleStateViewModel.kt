package com.androbrain.core.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val KEY_STATE = "STATE"

abstract class SingleStateViewModel<S : UiState>(
    private val savedStateHandle: SavedStateHandle,
    initialState: S
) :
    ViewModel() {

    private val mutableState = MutableStateFlow(savedStateHandle[KEY_STATE] ?: initialState)
    val state = mutableState.asStateFlow()

    fun updateState(action: (S) -> S) {
        mutableState.update(action)
    }

    override fun onCleared() {
        savedStateHandle[KEY_STATE] = state.value
        super.onCleared()
    }
}

interface UiState : Parcelable
