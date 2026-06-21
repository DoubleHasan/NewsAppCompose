package com.newsapp2.feature.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsapp2.domain.model.News
import com.newsapp2.feature.home.SearchNews

@Composable
fun SavedScreen(
    onHomeClicked: () -> Unit,
    onCardClicked: (News) -> Unit
) {
    val viewModel: SavedViewModel = hiltViewModel()
    val newsList = viewModel.savedNewsList.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(bottom = 4.dp)            ) {
                HorizontalDivider(thickness = 1.dp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp)
                        .padding(horizontal = 85.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.home_outlined),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onHomeClicked()
                            }
                    )

                    Image(
                        painter = painterResource(R.drawable.bookmark_filled),
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                            }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 27.dp, top = 16.dp, bottom = 31.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.blackpoint),
                    contentDescription = "Seklin tesviri",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "News Catcher", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
            }

            Text(
                text = "March 26th, 2022",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF89969C),
                modifier = Modifier.padding(start = 27.dp, bottom = 32.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(newsList.value) { item ->
                    SearchNews(item, onCardClicked = { onCardClicked(item) })
                }
            }
        }
    }
}