package com.example.todolistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.local.dao.TodoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val dao: TodoDao
):ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NONE_PRIORITY)
    private val _todo = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.HIGH_PRIORITY -> dao.getTodoOrderedByHighPriority()
                SortType.LOW_PRIORITY -> dao.getTodoOrderedByLowPriority()
                SortType.NONE_PRIORITY -> dao.getTodoOrderedByNone()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _searchTextState = MutableStateFlow("")
    private val searchTextState = _searchTextState.asStateFlow()

    val todo = searchTextState
        .onEach { _isSearching.update { true } }
        .combine(_todo){ text,todo ->
            if(text.isBlank()){
                todo
            } else{
                todo.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),_todo.value)

    private val _state = MutableStateFlow(TodoState())
    val state = combine(_state,_sortType,todo,searchTextState){ state,sortType,todo,searchTextState ->
        state.copy(
            todo = todo,
            sortType = sortType,
            searchTextState = searchTextState
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),TodoState())

    fun onEvent(event : TodoEvent){
        when(event){
            is TodoEvent.DeleteTodo -> {
                viewModelScope.launch{
                    dao.deleteTodo(event.todo)
                }
            }
            TodoEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingTodo = false
                ) }
            }
            TodoEvent.SaveTodo -> {
                val title = state.value.title
                val priority = state.value.priority
                val description = state.value.description

                if(title.isBlank() || description.isBlank()){
                    return
                }

                val todo = Todo(
                    title = title,
                    priority = priority,
                    description = description
                )
                viewModelScope.launch {
                    dao.upsertTodo(todo)
                }
                _state.update{it.copy(
                    isAddingTodo = false,
                    title = "",
                    description = "",
                    priority = 1
                )}
            }
            is TodoEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is TodoEvent.SetPriority -> {
                _state.update { it.copy(
                    priority = event.priority
                ) }
            }
            is TodoEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            TodoEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingTodo = true
                ) }
            }
            is TodoEvent.SortTodo -> {
                _sortType.value = event.sortType
            }
            is TodoEvent.ShowDrawerUI -> {
                _state.update { it.copy(
                    drawUI = true
                ) }
            }
            is TodoEvent.HideDrawerUI -> {
                _state.update { it.copy(
                    drawUI = false
                ) }
            }
            is TodoEvent.ShowExpandedUI -> {
                _state.update { it.copy(
                    expanded = true
                ) }
            }
            is TodoEvent.HideExpandedUI -> {
                _state.update { it.copy(
                    expanded = false
                ) }
            }

            is TodoEvent.UpdateSearchTextState -> {
                _searchTextState.value = event.searchTextState
            }
            is TodoEvent.UpdateSearchWidgetState -> {
                _state.update { it.copy(
                    searchWidgetState = event.searchWidgetState
                ) }
            }
        }
    }

}