package com.newsapp2.core

import androidx.compose.runtime.staticCompositionLocalOf

enum class Language {
    EN, RU
}

val LocalAppLanguage = staticCompositionLocalOf { Language.EN.name }
val LocalUpdateLanguage = staticCompositionLocalOf<(String) -> Unit> { {} }