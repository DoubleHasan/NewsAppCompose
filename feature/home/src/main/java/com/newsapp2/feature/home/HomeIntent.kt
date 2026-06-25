package com.newsapp2.feature.home

interface HomeIntent {
    data class OnQueryChange(val text: String) : HomeIntent
}