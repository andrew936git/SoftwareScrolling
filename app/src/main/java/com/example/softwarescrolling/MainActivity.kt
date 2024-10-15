package com.example.softwarescrolling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            val groups = Person.persons.groupBy{ it.post }
            CreateLazyColumn(listState, coroutineScope, groups)

        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun CreateLazyColumn(
        listState: LazyListState,
        coroutineScope: CoroutineScope,
        groups: Map<String, List<Person>>
    ) {
        LazyColumn(
            Modifier.padding(top = 30.dp),
            contentPadding = PaddingValues(6.dp),
            state = listState
        ) {
            item {
                Text(
                    text = "В конец",
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(2.dp, Color.Magenta)
                        .padding(6.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(Person.persons.size - 1)
                            }
                        }
                )
            }
            groups.forEach { (post, names) ->
                stickyHeader {
                    Text(
                        text = post,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(6.dp)
                            .background(Color.Magenta)
                            .fillParentMaxWidth()
                    )
                }
                items(names.sortedBy { it.surname }) { person ->
                    Text(
                        text = "${person.surname} ${person.name}",
                        fontSize = 28.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
            item {
                Text(
                    text = "В начало",
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(2.dp, Color.Magenta)
                        .padding(6.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                )
            }
        }
    }
}

