package com.example.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.common.ui.ErrorScreen
import com.example.common.ui.LoadingScreen
import com.example.model.ImageData
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val searchText by viewModel.searchText.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val searchResult = viewModel.searchResult.collectAsLazyPagingItems()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        SearchField(
            searchText = searchText,
            onSearchTextChanged = { viewModel.updateSearchText(it) },
            onTextClear = { viewModel.clearSearchText() })

        when (uiState) {
            is SearchUiState.Loading -> {
                LoadingScreen()
            }

            is SearchUiState.Success -> {
                if (searchResult.itemCount == 0 && searchResult.loadState.refresh is LoadState.NotLoading) {
                    val errorMsg = stringResource(id = com.example.common.R.string.errorNoResult)
                    LaunchedEffect(key1 = Unit) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    ImageList(searchResult)
                }

            }

            is SearchUiState.Error -> {
                Timber.d("sjh error")
                LaunchedEffect(key1 = Unit) {
                    Toast.makeText(context, (uiState as SearchUiState.Error).e?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                ErrorScreen(stringResource(id = com.example.common.R.string.errorMsg))
            }

            is SearchUiState.Default -> {
            }
        }
    }
}

@Composable
private fun SearchField(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onTextClear: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester),
            singleLine = true,
            placeholder = { Text("이미지 키워드 검색") },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { onTextClear() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "clear button"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@Composable
private fun ImageList(searchResult: LazyPagingItems<ImageData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = searchResult.itemCount
        ) {
            SearchItem(searchResult[it])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SearchItem(item: ImageData?) {
    var isBookmarked by remember { mutableStateOf(false) }

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .encodeQuality(80)
        .format(DecodeFormat.PREFER_RGB_565)
        .centerInside()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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
            onClick = { isBookmarked = !isBookmarked },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(color = Color.Gray)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "clear button",
                tint = if (!isBookmarked) Color.White else Color.Yellow
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