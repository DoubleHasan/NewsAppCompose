package com.newsapp2.core.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalAppLanguage = staticCompositionLocalOf { "en" }
val LocalUpdateLanguage = staticCompositionLocalOf<(String) -> Unit> { {} }
