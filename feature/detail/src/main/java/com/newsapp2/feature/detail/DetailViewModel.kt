package com.newsapp2.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp2.data.mapper.toEntity
import com.newsapp2.data.room.UserDao
import com.newsapp2.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {

    fun saveNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertNews(news.toEntity())
        }
    }

    fun deleteNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteNews(news.toEntity())
        }
    }

    fun isSaved(url: String): Flow<Boolean> {
        return userDao.isNewsSaved(url)
    }
}