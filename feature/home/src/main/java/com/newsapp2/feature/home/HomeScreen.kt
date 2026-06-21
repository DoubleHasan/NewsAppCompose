package com.newsapp2.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.newsapp2.core.network.LocalAppLanguage
import com.newsapp2.domain.model.News

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNewsClicked: (News) -> Unit,
    onLangCLick: () -> Unit,
    onBookmarkClicked: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var searchText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val searchState by viewModel.searchUiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(Color.White)
            ) {
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
                        painter = painterResource(
                            if (selectedItem == 0) R.drawable.home_filled
                            else R.drawable.home_outlined
                        ),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { selectedItem = 0 }
                    )

                    Image(
                        painter = painterResource(
                            if (selectedItem == 1) R.drawable.bookmark_filled
                            else R.drawable.bookmark_outlined
                        ),
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedItem = 1
                                onBookmarkClicked()
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
            TopBar()

            when (val currentState = uiState) {
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }

                is UIState.Success -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentPadding = PaddingValues(start = 27.dp, end = 42.88.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(currentState.newsList) { item ->
                            PopularNewsCard(
                                news = item,
                                modifier = Modifier.clickable { onNewsClicked(item) }
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentState.message, color = Color.Red, fontSize = 14.sp)
                    }
                }
            }

            SearchTextField(
                searchText = searchText.trim(),
                onValueChange = {
                    searchText = it
                    if (it.trim().isEmpty()) {
                        viewModel.searchNews("finance")
                    } else {
                        viewModel.searchNews(searchText)
                    }
                }
            )

            Spacer(modifier = Modifier.height(13.dp))

            LanguageSwitchButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 23.dp),
                onLangCLick = onLangCLick
            )

            Spacer(modifier = Modifier.height(23.dp))

            when (val currentState = searchState) {
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }

                is UIState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(currentState.newsList) { item ->
                            SearchNews(
                                news = item,
                                onCardClicked = { onNewsClicked(item) }
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentState.message, color = Color.Red, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 27.dp, top = 16.dp, bottom = 31.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.blackpoint),
            contentDescription = "Point",
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(com.example.newsapp2.core.network.R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

    Text(
        text = "March 26th, 2022",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF89969C),
        modifier = Modifier.padding(start = 27.dp, bottom = 16.dp)
    )
}

@Composable
fun SearchTextField(
    searchText: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(com.example.newsapp2.core.network.R.string.search_hint),
                fontSize = 11.sp,
                color = Color(0xFFCDC8C8)
            )
        },
        shape = RoundedCornerShape(size = 16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE2E2E2),
            unfocusedBorderColor = Color(0xFFE2E2E2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        singleLine = true,
        modifier = Modifier
            .padding(start = 27.dp, top = 16.dp, end = 27.dp)
            .fillMaxWidth()
            .height(54.dp)
    )
}

@Composable
fun LanguageSwitchButton(
    modifier: Modifier = Modifier,
    onLangCLick: () -> Unit
) {
    val currentLocale = LocalAppLanguage.current
    val isEnglish = currentLocale == "en"

    Box(
        modifier = modifier
            .background(
                color = Color(0xFFD7D7D7).copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            )
            .width(75.dp)
            .height(26.dp)
            .clickable { onLangCLick() }

    ) {
        Text(
            text = if (isEnglish) "EN" else "RU",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 8.dp)
                .size(16.dp)
                .background(
                    color = Color(0xFFB0B0B0).copy(alpha = 0.5f),
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(10.dp)
                    .background(color = Color(0xFFA54B4B), shape = CircleShape)
            )
        }
    }
}

@Composable
fun PopularNewsCard(news: News, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(300.dp)
            .height(180.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = news.urlToImage,
                contentDescription = "Popular News Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.sampleimage),
                error = painterResource(R.drawable.sampleimage)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 18.dp, top = 18.dp)
                    .heightIn(22.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .widthIn(min = 86.dp)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sport",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Text(
                text = news.title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 18.dp, bottom = 18.dp, end = 18.dp)
            )
        }
    }
}

@Composable
fun SearchNews(news: News, modifier: Modifier = Modifier, onCardClicked: (News) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 27.dp)
            .clickable(onClick = { onCardClicked(news) }),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF3F4F6),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Finance",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = news.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (news.publishedAt.length > 10)
                            news.publishedAt.take(10) else news.publishedAt,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF89969C),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = if (news.author.length > 10)
                            news.author.take(10) else news.author,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            AsyncImage(
                model = news.urlToImage,
                contentDescription = "News Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(102.dp)
                    .clip(RoundedCornerShape(12.dp)),
                placeholder = painterResource(R.drawable.sampleimage),
                error = painterResource(R.drawable.sampleimage)
            )
        }
    }
}