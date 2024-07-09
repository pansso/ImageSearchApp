package com.example.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.common.ui.BaseTextScreen
import com.example.common.ui.LoadingScreen
import com.example.model.ImageData
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

val LARGE_DISPLAY_WIDTH = 600
@Composable
fun SearchScreen(
    searchText: StateFlow<String>,
    viewModel: SearchViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.debounceSearchText(searchText)
        viewModel.getAllBookmarks()
    }
    val uiState by viewModel.uiState.collectAsState()
    val searchResult = viewModel.searchResult.collectAsLazyPagingItems()
    val bookmarkList by viewModel.bookmarkList.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState) {
            is SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Error -> BaseTextScreen(stringResource(id = com.example.common.R.string.errorMsg))
            is SearchUiState.Default -> BaseTextScreen(msg = stringResource(id = com.example.common.R.string.noSearchResultFound))
            is SearchUiState.Success -> {
                when (searchResult.loadState.refresh) {
                    is LoadState.Error -> BaseTextScreen(stringResource(id = com.example.common.R.string.errorMsg))
                    is LoadState.Loading -> LoadingScreen()
                    else -> {
                        if (searchResult.itemCount == 0 && searchResult.loadState.refresh is LoadState.NotLoading) {
                            //불러올수 있는 아이템이 없음
                            LaunchedEffect(Unit) {
                                Toast.makeText(context, com.example.common.R.string.errorNoResult, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            //성공부분
                            ImageList(
                                searchResult = searchResult,
                                bookmarkList = bookmarkList,
                                onBookmarkToggle = {
                                    viewModel.toggledBookmark(
                                        it,
                                        searchText.value
                                    )
                                })
                        }
                    }
                }
            }

        }
    }
}


@Composable
private fun ImageList(
    searchResult: LazyPagingItems<ImageData>,
    bookmarkList: List<String>,
    onBookmarkToggle: (String) -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val gridListState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }
        val screenWidth = maxWidth
        if (screenWidth> LARGE_DISPLAY_WIDTH.dp){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                state = gridListState
            ) {
                items(searchResult.itemCount) { index ->
                    SearchItem(searchResult[index], bookmarkList, onBookmarkToggle)
                }
            }
        } else{
            val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(
                    count = searchResult.itemCount
                ) {
                    SearchItem(searchResult[it], bookmarkList, onBookmarkToggle)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}

@Composable
private fun SearchItem(
    item: ImageData?,
    bookmarkList: List<String>,
    onBookmarkToggle: (String) -> Unit,
    padding: Dp = 8.dp
) {
//    val isBookmark = item?.imageUrl in bookmarkList
    val isBookmark by remember(
        item?.imageUrl,
        bookmarkList
    ) { derivedStateOf { item?.imageUrl in bookmarkList } }
    val requestOptions = remember {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .encodeQuality(80)
            .format(DecodeFormat.PREFER_RGB_565)
            .centerInside()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(padding)
    ) {
        GlideImage(
            imageModel = item?.imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            requestOptions = { requestOptions },
            placeHolder = painterResource(id = com.example.common.R.drawable.baseline_downloading_24),
            error = painterResource(id = com.example.common.R.drawable.baseline_error_outline_24)
        )
        IconButton(
            onClick = { item?.imageUrl?.let { onBookmarkToggle(it) } },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(color = Color.Gray)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "bookmark button",
                tint = if (isBookmark) Color.Yellow else Color.White
            )
        }
    }
}

@Preview
@Composable
private fun Prev() {
    val searchText = "testText"
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = searchText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            placeholder = { Text("이미지 키워드 검색") },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "clear button"
                        )
                    }
                }
            }
        )

    }
}
