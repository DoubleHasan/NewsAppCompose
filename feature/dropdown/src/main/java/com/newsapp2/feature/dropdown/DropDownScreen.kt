package com.newsapp2.feature.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsapp2.core.Language
import com.newsapp2.core.LocalUpdateLanguage

@Composable
fun DropDownScreen(onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .width(63.dp)
                .height(45.dp)
                .padding(start = 15.dp, top = 18.dp)
                .background(
                    color = Color(0xFFD7D7D7).copy(alpha = 0.6f),
                    shape = RoundedCornerShape(32.dp)
                )
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "arrowback",
                modifier = Modifier.size(14.dp)
            )
        }

        val changeLanguage = LocalUpdateLanguage.current

        LangList(Language.EN.name, onClick = {
            changeLanguage(Language.EN.name)
            onBackClick()
        })

        LangList(Language.RU.name, onClick = {
            changeLanguage(Language.RU.name)
            onBackClick()
        })
    }
}

@Composable
fun LangList(language: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            language,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 28.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)
    }
}