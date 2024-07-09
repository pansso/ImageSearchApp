package com.example.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.common.R
import com.example.common.ui.BaseTextScreen
import com.example.common.ui.SearchField
import com.example.model.BookmarkData
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BookmarkScreen(
    searchText: StateFlow<String>,
    viewModel: BookmarkViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.debounceBookmarkText(searchText)
        viewModel.refreshBookmarkList()
    }

    val displayList by viewModel.displayList.collectAsState()
    val checkItems by viewModel.checkItems.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = com.example.common.R.string.checkCount,
                    checkItems.size
                )
            )
            Button(onClick = {
                viewModel.deleteBookmarkList(checkItems.toList())
            }, enabled = checkItems.isNotEmpty()) {
                Text(text = stringResource(id = com.example.common.R.string.remove))
            }
        }


        when (uiState) {
            is BookmarkUiState.Error -> {
                BaseTextScreen(msg = stringResource(id = R.string.errorMsg))
            }

            else -> {
                if (displayList.isEmpty()) {
                    BaseTextScreen(msg = stringResource(id = R.string.noSearchResultFound))
                } else {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val gridListState =
                            rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }
                        val screenWidth = maxWidth
                        if (screenWidth > 600.dp) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                state = gridListState
                            ) {
                                items(
                                    count = displayList.size
                                ) {
                                    BookmarkItem(
                                        bookmark = displayList[it],
                                        isChecked = checkItems.contains(displayList[it].imageUrl),
                                        onCheckChange = { isChecked ->
                                            viewModel.toggleCheckItem(url = displayList[it].imageUrl)
                                        },
                                        onDelete = {
                                            viewModel.deleteBookmark(displayList[it].imageUrl)
                                        })

                                }
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(
                                    count = displayList.size
                                ) {
                                    BookmarkItem(
                                        bookmark = displayList[it],
                                        isChecked = checkItems.contains(displayList[it].imageUrl),
                                        onCheckChange = { isChecked ->
                                            viewModel.toggleCheckItem(url = displayList[it].imageUrl)
                                        },
                                        onDelete = {
                                            viewModel.deleteBookmark(displayList[it].imageUrl)
                                        })

                                }
                            }
                        }
                    }

                }
            }
        }
    }


}

@Composable
fun BookmarkItem(
    bookmark: BookmarkData,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val requestOptions = remember {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .encodeQuality(80)
            .format(DecodeFormat.PREFER_RGB_565)
            .centerInside()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckChange
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            GlideImage(
                imageModel = bookmark.imageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                requestOptions = { requestOptions },
                placeHolder = painterResource(id = R.drawable.baseline_downloading_24),
                error = painterResource(id = R.drawable.baseline_error_outline_24)
            )
        }

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete bookmark")
        }
    }
}