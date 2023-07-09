package com.example.todolistapp.screens.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todolistapp.TodoEvent
import com.example.todolistapp.TodoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    state: TodoState,
    onEvent: (TodoEvent)->Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){

    val icon = if (state.expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(TodoEvent.HideDialog)
        },
        title = { Text(text = "Add Todo")},
        text = {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = {onEvent(TodoEvent.SetTitle(it))},
                    placeholder = {
                        Text(text = "Title")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Box(
                    modifier = modifier
                ){
                    OutlinedTextField(
                        value = when(state.priority){
                            0 -> "LOW"
                            2 -> "HIGH"
                            else -> "NONE"
                        },
                        onValueChange = {},
                        readOnly = true,
                        modifier = modifier.clickable {
                            if(state.expanded){
                                onEvent(TodoEvent.HideExpandedUI)
                            } else{
                                onEvent(TodoEvent.ShowExpandedUI)
                            }
                        },
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = icon, contentDescription = "Arrow Menu"
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = state.expanded,
                        onDismissRequest = { onEvent(TodoEvent.HideExpandedUI) },
                        modifier = modifier.width(272.dp)
                    ) {
                        Drawer2(onEvent,modifier)
                    }
                }
                OutlinedTextField(
                    value = state.description,
                    onValueChange = {onEvent(TodoEvent.SetDescription(it))},
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = modifier.fillMaxHeight(),
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEvent(TodoEvent.SaveTodo)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "ADD : ${state.title}"
                                )
                            }
                        }
                    )
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(
                    onClick = {
//                        println(state.title)
                        onEvent(TodoEvent.SaveTodo)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "ADD : ${state.title}"
                            )
                        }
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    )

}

@Composable
fun Drawer2(
    onEvent: (TodoEvent)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(TodoEvent.SetPriority(0))
                    onEvent(TodoEvent.HideExpandedUI)
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
                    onEvent(TodoEvent.SetPriority(2))
                    onEvent(TodoEvent.HideExpandedUI)
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
                    onEvent(TodoEvent.SetPriority(1))
                    onEvent(TodoEvent.HideExpandedUI)
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