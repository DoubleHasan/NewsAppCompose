package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.NewsNavHost
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.newsapp2.core.network.LocalAppLanguage
import com.newsapp2.core.network.LocalUpdateLanguage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentLanguage by remember {
                mutableStateOf(
                    AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "en"
                )
            }

            val locale = java.util.Locale(currentLanguage)
            java.util.Locale.setDefault(locale)
            val resources = this.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            key(currentLanguage) {
                NewsAppTheme {
                    val navController = rememberNavController()

                    CompositionLocalProvider(
                        LocalAppLanguage provides currentLanguage,
                        LocalUpdateLanguage provides { nextLang ->
                            currentLanguage = nextLang
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(nextLang)
                            )
                        }
                    ) {
                        NewsNavHost(
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}