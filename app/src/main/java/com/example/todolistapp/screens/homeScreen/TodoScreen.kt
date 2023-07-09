package com.example.todolistapp.screens.homeScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.SearchWidgetState
import com.example.todolistapp.SortType
import com.example.todolistapp.TodoEvent
import com.example.todolistapp.TodoState
import com.example.todolistapp.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    state: TodoState,
    onEvent: (TodoEvent)->Unit,
    modifier: Modifier = Modifier
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MainAppBar(
                state = state,
                onTextChange = { onEvent(TodoEvent.UpdateSearchTextState(searchTextState = it)) },
                onCloseClicked = { onEvent(TodoEvent.UpdateSearchWidgetState(searchWidgetState = SearchWidgetState.CLOSED)) },
                onSearchClicked = { Log.d("Searched Text: ",it) },
                onSearchTriggered = { onEvent(TodoEvent.UpdateSearchWidgetState(searchWidgetState = SearchWidgetState.OPENED)) },
                onEvent = onEvent
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TodoEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        },
        content = { padding ->
            if(state.isAddingTodo){
                AddContactDialog(state = state, onEvent = onEvent,scope = scope, snackbarHostState = snackbarHostState)
            }
            LazyColumn(
                modifier = modifier
                    .padding(padding)
            ) {
                items(state.todo){ todo->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = todo.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = todo.description,
                                fontSize = 12.sp
                            )
                        }
                        when(todo.priority){
                            0 -> {
                                Column(
                                    modifier = modifier
                                        .height(50.dp)
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Canvas(modifier = modifier.size(size = 20.dp)){
                                        drawCircle(
                                            color = Color.Green,
                                            radius = 7.dp.toPx()
                                        )
                                    }
                                }
                            }
                            1 -> {
                                Column(
                                    modifier = modifier
                                        .height(50.dp)
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Canvas(modifier = modifier.size(size = 20.dp)){
                                        drawCircle(
                                            color = Color.Gray,
                                            radius = 7.dp.toPx()
                                        )
                                    }
                                }
                            }
                            2 -> {
                                Column(
                                    modifier = modifier
                                        .height(50.dp)
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Canvas(modifier = modifier.size(size = 20.dp)){
                                        drawCircle(
                                            color = Color.Red,
                                            radius = 7.dp.toPx()
                                        )
                                    }
                                }
                            }
                        }
                        IconButton(onClick = {
                            onEvent(TodoEvent.DeleteTodo(todo))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete, contentDescription = "Delete Contact"
                            )
                        }
                    }
                }
            }
        }

    )
}

@Composable
fun Drawer(
    onEvent: (TodoEvent)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(TodoEvent.SortTodo(sortType = SortType.LOW_PRIORITY))
                    onEvent(TodoEvent.HideDrawerUI)
                }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            ) {
                Canvas(modifier = modifier.size(size = 20.dp)){
                    drawCircle(
                        color = Color.Green,
                        radius = 7.dp.toPx()
                    )
                }
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "LOW",
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(TodoEvent.SortTodo(sortType = SortType.HIGH_PRIORITY))
                    onEvent(TodoEvent.HideDrawerUI)
                }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            ) {
                Canvas(modifier = modifier.size(size = 20.dp)){
                    drawCircle(
                        color = Color.Red,
                        radius = 7.dp.toPx()
                    )
                }
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "HIGH",
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(TodoEvent.SortTodo(sortType = SortType.NONE_PRIORITY))
                    onEvent(TodoEvent.HideDrawerUI)
                }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            ) {
                Canvas(modifier = modifier.size(size = 20.dp)){
                    drawCircle(
                        color = Color.Gray,
                        radius = 7.dp.toPx()
                    )
                }
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "NONE",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MainAppBar(
    state: TodoState,
    onEvent: (TodoEvent)->Unit,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
){
    when(state.searchWidgetState){
        SearchWidgetState.OPENED -> { SearchAppBar(
            onTextChange = onTextChange,
            onCloseClicked = onCloseClicked,
            onSearchClicked = onSearchClicked,
            state = state
        ) }
        SearchWidgetState.CLOSED -> { DefaultAppBar(
            state = state,
            onEvent = onEvent,
            onSearchClicked = onSearchTriggered
        ) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    state: TodoState,
    onEvent: (TodoEvent)->Unit,
    onSearchClicked: () -> Unit
){
    TopAppBar(
        title = { Text(text = "Tasks", color = Color.White) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Purple80
            }
        ),
        actions = {
            Row(
                modifier = Modifier
            ) {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                ) {
                    IconButton(onClick = {
                        if(state.drawUI){
                            onEvent(TodoEvent.HideDrawerUI)
                        } else{
                            onEvent(TodoEvent.ShowDrawerUI)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Sort By",
                            modifier = Modifier
                                .padding(start = 0.dp),
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = state.drawUI,
                        onDismissRequest = { onEvent(TodoEvent.HideDrawerUI) }
                    ) {
                        Drawer(onEvent,Modifier)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    state: TodoState,
    onTextChange: (String)->Unit,
    onCloseClicked: ()->Unit,
    onSearchClicked: (String)->Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = if (isSystemInDarkTheme()) {
            Color.Black
        } else {
            Purple80
        }
    ) {
        TextField(
            modifier = Modifier,
            value = state.searchTextState,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.38f),
                    text = "Search Here...",
                    color = Color.White
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.38f),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if(state.searchTextState.isNotEmpty()){
                            onTextChange("")
                        } else{
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(state.searchTextState) }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.White.copy(alpha = 0.38f)
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTopAppBar(){
    TopAppBar(
        title = { Text(text = "Tasks", color = Color.White) },
        modifier = Modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Purple80
            }
        ),
        actions = {
            Row(modifier=Modifier) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon",
                        tint = Color.White)
                }
                Box(
                    modifier = Modifier
                ) {

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Sort By",
                            modifier = Modifier
                                .padding(start = 0.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchAppBar(){
    val text: String = "Search"
    val onTextChange = {}
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = if (isSystemInDarkTheme()) {
            Color.Black
        } else {
            Purple80
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.38f),
                    text = "Search Here...",
                    color = Color.White
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.38f),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
//                    keyboardActions = KeyboardActions(
//                        onSearch = { onSearchClicked(text) }
//                    ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.White.copy(alpha = 0.38f)
            )
        )
    }
}
