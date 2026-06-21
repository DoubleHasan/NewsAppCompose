package com.newsapp2.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp2.feature.home.UIState
import com.newsapp2.domain.model.News
import com.newsapp2.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchUiState = MutableStateFlow<UIState>(UIState.Loading)
    val searchUiState = _searchUiState.asStateFlow()

    init {
        getAllNews("sport")
        searchNews("finance")
    }

    fun getAllNews(query: String) {

        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val news = useCase(query)
                _uiState.value = UIState.Success(news)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error")
            }

        }
    }

    fun searchNews(query: String){

        viewModelScope.launch {
            _searchUiState.value = UIState.Loading
            try {
                val news = useCase(query)
                _searchUiState.value = UIState.Success(news)
            } catch (e: Exception) {
                _searchUiState.value = UIState.Error(e.message ?: "Error")
            }
        }
    }
}