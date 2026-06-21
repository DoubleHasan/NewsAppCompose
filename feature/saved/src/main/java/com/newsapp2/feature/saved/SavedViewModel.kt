package com.newsapp2.feature.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp2.data.mapper.toDomain
import com.newsapp2.data.room.UserDao
import com.newsapp2.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    val savedNewsList: StateFlow<List<News>> = userDao.getAllSavedNews()
        .map { entityList ->
            entityList.map { entity -> entity.toDomain() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}