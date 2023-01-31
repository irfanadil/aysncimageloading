package com.offline.imagedemo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.UrlViewState
import com.offline.imagedemo.screen.repo.OpenAiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val openAiRepo: OpenAiRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UrlViewState>(UrlViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    fun getLargeImage(imageDetail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            openAiRepo.loadImage(imageDetail).distinctUntilChanged().collect { result ->
                when (result) {
                    is GenericApiResponse.Success -> {
                        _uiState.value = UrlViewState.Success(result.value)
                    }

                    is GenericApiResponse.Failure -> {
                        result.message?.let {
                            _uiState.value = UrlViewState.Error(it)
                        }
                    }

                    else -> {
                        _uiState.value = UrlViewState.Empty
                        //_uiState.value = UrlViewState.Success(result)
                    }
                }
            }
        }
    }
}