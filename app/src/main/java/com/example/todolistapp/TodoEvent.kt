package com.example.todolistapp

sealed interface TodoEvent{
    object SaveTodo: TodoEvent
    data class SetTitle(val title: String): TodoEvent
    data class SetPriority(val priority: Int): TodoEvent
    data class SetDescription(val description: String): TodoEvent
    object ShowDialog: TodoEvent
    object HideDialog: TodoEvent
    data class SortTodo(val sortType: SortType): TodoEvent
    data class DeleteTodo(val todo: Todo): TodoEvent
    object ShowDrawerUI: TodoEvent
    object HideDrawerUI: TodoEvent
    object ShowExpandedUI: TodoEvent
    object HideExpandedUI: TodoEvent
    data class UpdateSearchWidgetState(val searchWidgetState: SearchWidgetState): TodoEvent
    data class UpdateSearchTextState(val searchTextState: String): TodoEvent
}