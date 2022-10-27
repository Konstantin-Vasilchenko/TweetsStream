package com.example.tweetssearchapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tweetssearchapp.R
import com.example.tweetssearchapp.domain.model.StreamDataUi

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(
    tweets: List<StreamDataUi> = listOf(StreamDataUi("Example")),
    onSearchBtnClick: (String) -> Unit = {}
) {
    val searchText = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            backgroundColor = Color(0xFF009688)
        )

        Divider(modifier = Modifier.padding(5.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TextField(
                value = searchText.value, onValueChange = { searchText.value = it },
                modifier = Modifier
                    .weight(weight = 0.7f)
                    .fillMaxWidth(),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp, color = Color.Black),
                singleLine = true,
            )
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp),
                onClick = {
                    keyboardController?.hide()
                    if (searchText.value.isNotEmpty()) {
                        onSearchBtnClick(searchText.value)
                    }
                }
            ) {
                Text(text = "Search", style = MaterialTheme.typography.button)
            }
        }

        LazyColumn(modifier = Modifier.padding(top = 5.dp)) {
            items(tweets) { tweet ->
                TweetRow(text = tweet.text)
            }
        }
    }
}

@Composable
fun TweetRow(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0xFFE3F2FD),
        elevation = 6.dp
    ) {
        Text(
            text = text,
            modifier = modifier.padding(5.dp),
            color = Color.Black,
            style = MaterialTheme.typography.body2
        )
    }
}
