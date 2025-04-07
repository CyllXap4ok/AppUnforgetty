package com.helpyapps.unforgetty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.helpyapps.unforgetty.model.tasks_db.TaskEntity
import com.helpyapps.unforgetty.model.navigation.Main
import com.helpyapps.unforgetty.view.screen.main.MainScreen
import com.helpyapps.unforgetty.view.screen.task.TaskScreen
import com.helpyapps.unforgetty.ui.theme.UnforgettyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            UnforgettyTheme {
                Surface(Modifier.fillMaxSize()) {
                    Surface(Modifier.fillMaxSize().padding(10.dp)) {

                        NavHost(navController, startDestination = Main) {

                            composable<Main> {
                                MainScreen() { taskData ->
                                    navController.navigate(taskData)
                                }
                            }

                            composable<TaskEntity> { backStackEntry ->
                                val taskData = backStackEntry.toRoute<TaskEntity>()
                                TaskScreen(taskData)
                            }

                        }

                    }
                }
            }

        }
    }
}