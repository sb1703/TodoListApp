package com.example.todolistapp

data class TodoState(
    val todo: List<Todo> = emptyList(),                             // sorting list
    val title: String = "",
    val priority: Int = 0,
    val description: String = "",
    val isAddingTodo: Boolean = false,
    val sortType: SortType = SortType.NONE_PRIORITY,
    val drawUI: Boolean = false,
    val expanded: Boolean = false,
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
    val searchTextState: String = ""
)
