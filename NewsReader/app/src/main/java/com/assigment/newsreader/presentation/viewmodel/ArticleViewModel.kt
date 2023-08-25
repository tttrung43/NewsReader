package com.assigment.newsreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assigment.newsreader.common.Resource
import com.assigment.newsreader.domain.use_case.FetchNewsUseCase
import com.assigment.newsreader.presentation.uistate.ArticleInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ArticleInfoState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun fetchNews() {
        viewModelScope.launch {
            fetchNewsUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowErrorMessage(
                                    result.message ?: "Unknown message"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowErrorMessage(val message: String) : UIEvent()
    }
}