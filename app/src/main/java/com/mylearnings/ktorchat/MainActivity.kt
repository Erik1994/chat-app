package com.mylearnings.ktorchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mylearnings.ktorchat.presentation.chat.ChatScreen
import com.mylearnings.ktorchat.presentation.constant.Navigation
import com.mylearnings.ktorchat.presentation.username.UsernameScreen
import com.mylearnings.ktorchat.ui.theme.KtorChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.USERNAME_SCREEN
                    ) {
                        composable(route = Navigation.USERNAME_SCREEN) {
                            UsernameScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = "${Navigation.CHAT_SCREEN}/{${Navigation.USERNAME}}",
                            arguments = listOf(
                                navArgument(name = Navigation.USERNAME) {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) {
                            val username = it.arguments?.getString(Navigation.USERNAME)
                            ChatScreen(username = username)
                        }
                    }
                }
            }
        }
    }
}
