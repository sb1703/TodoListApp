package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.navigation.SetupNavGraph
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            TodoDatabase::class.java,
//            "todo.db"
//        ).build()
//    }

//    Factory (Design Pattern)
//    private val viewModel by viewModels<TodoViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory{
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return TodoViewModel(db.dao) as T
//                }
//            }
//        }
//    )
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
//                System Bar Color
//                window.statusBarColor = ContextCompat.getColor(this, R.color.white)

                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}