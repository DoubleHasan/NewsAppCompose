package com.newsapp2.feature.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Async
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.newsapp2.domain.model.News
import org.jetbrains.annotations.Async
import androidx.core.net.toUri

@Composable
fun DetailScreen(news: News, onBackClick: () -> Unit) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var isMenuExpanded by remember { mutableStateOf(false) }
    val viewModel: DetailViewModel = hiltViewModel()
    val isSaved by viewModel.isSaved(news.url).collectAsState(false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = news.urlToImage,
                contentDescription = "NewsImage",
                placeholder = painterResource(R.drawable.sampleimage),
                error = painterResource(R.drawable.sampleimage)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 22.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .width(90.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = { onBackClick() })
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Black,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = news.title,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(width = 32.dp, height = 32.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable(onClick = { isMenuExpanded = true }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Black
                    )
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(
                                            if (isSaved) R.drawable.bookmark_filled
                                            else R.drawable.bookmark_outlined
                                        ),
                                        contentDescription = "Save",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = if (isSaved) stringResource(com.newsapp2.core.R.string.unsave) else stringResource(
                                            com.newsapp2.core.R.string.save
                                        )
                                    )
                                }
                            },
                            onClick = {
                                if (isSaved) {
                                    viewModel.deleteNews(news)
                                } else {
                                    isMenuExpanded = false
                                    viewModel.saveNews(news)
                                }
                            }
                        )

                        androidx.compose.material3.HorizontalDivider(
                            thickness = 1.dp,
                            color = Color(0xFFE2E2E2)
                        )

                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Unspecified
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(text = stringResource(com.newsapp2.core.R.string.share))
                                }
                            },
                            onClick = {
                                isMenuExpanded = false
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, news.title)
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "${news.title}\n\nRead here: ${news.url}"
                                    )
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "Share via"
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 191.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 28.dp, start = 33.dp)
                    .background(
                        color = Color(0xFFF3F4F6),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Sport",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = news.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 37.dp, end = 34.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = news.author,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 34.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = news.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 37.dp, end = 34.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = news.description,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF89969C),
                modifier = Modifier
                    .padding(start = 33.dp, end = 34.dp),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(49.dp))

            Text(
                text = stringResource(com.newsapp2.core.R.string.read_more),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF57A5D1),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 33.dp, bottom = 24.dp)
                    .clickable(onClick = {
                        if (news.url.isNotEmpty()) {
                            val intent = Intent(Intent.ACTION_VIEW, news.url.toUri())
                            context.startActivity(intent)
                        }
                    })
            )
        }
    }
}