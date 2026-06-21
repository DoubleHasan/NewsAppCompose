package com.newsapp2.feature.home

import com.newsapp2.domain.model.News

sealed interface UIState {
    object Loading : UIState
    data class Success(val newsList : List<News>) : UIState
    data class Error(val message : String) : UIState
}