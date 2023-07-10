package com.example.wishlist_android

import SignIn
import SignUp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wishlist_android.pages.LoadingPage
import com.example.wishlist_android.pages.Wishlist
import com.example.wishlist_android.ui.theme.WishlistandroidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishlistandroidTheme {
                val backgroundColor = MaterialTheme.colorScheme.background
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )


                    onDispose {}
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = backgroundColor
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "loading"
                    ) {
                        composable("signIn") {
                            SignIn(navController = navController)
                        }

                        composable("signUp") {
                            SignUp(navController = navController)
                        }

                        composable("loading") {
                            LoadingPage(navController = navController)
                        }

                        composable("wishlist") {
                            Wishlist(navController = navController)
                        }
                    }
                }
            }
        }
    }
}