package com.newsapp2.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var getAllNews = emptyList<News>()
    private var searchNews = emptyList<News>()
    private val _searchText = MutableStateFlow<String>("")
    var searchText = _searchText.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {

        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getAllNews = useCase("sport")
                searchNews = useCase("finance")
                _uiState.value = UIState.Success(getAllNews, searchNews)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error")
            }

        }
    }

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
        val query = newText.trimIfEmpty { "finance" }

        viewModelScope.launch {
            try {
                searchNews = useCase(query)
                _uiState.value = UIState.Success(
                    newsList = getAllNews,
                    searchList = searchNews
                )
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error")
            }
        }
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnQueryChange -> onSearchTextChanged(intent.text)
        }
    }

    private fun String.trimIfEmpty(default: () -> String): String {
        return if (this.trim().isEmpty()) default() else this
    }
}