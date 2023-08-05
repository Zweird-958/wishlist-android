package com.example.wishlist_android

import SignIn
import SignUp
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.pages.AddWish
import com.example.wishlist_android.pages.EditWish
import com.example.wishlist_android.pages.LoadingPage
import com.example.wishlist_android.pages.Profile
import com.example.wishlist_android.pages.Retry
import com.example.wishlist_android.pages.Wishlist
import com.example.wishlist_android.ui.theme.WishlistandroidTheme
import com.example.wishlist_android.utils.getLanguage
import com.example.wishlist_android.utils.saveLanguage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var wishApi: WishApi

        private fun getWishApi(context: Context): WishApi {
            if (!::wishApi.isInitialized) {
                wishApi = RetrofitHelper.getInstance(context).create(WishApi::class.java)
            }
            return wishApi
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val language = getLanguage(newBase!!)
        val locale = if (language != null) Locale(language) else null

        val configuration = newBase.resources?.configuration

        if (locale != null) {
            configuration?.setLocale(locale)
            Locale.setDefault(locale)
        }

        if (!config.languagesDisplayed.keys.contains(Locale.getDefault().language)) {
            Locale.setDefault(Locale(config.defaultLanguage))
        }

        val context = configuration?.let { newBase.createConfigurationContext(it) }
        super.attachBaseContext(context)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWishApi(this)
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

                        composable("profile") {
                            Profile(navController = navController)
                        }

                        composable("addWish") {
                            AddWish(navController = navController)
                        }

                        composable(
                            "wish/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            EditWish(navController, backStackEntry.arguments?.getInt("id"))
                        }

                        composable("retry") {
                            Retry(navController = navController)
                        }
                    }
                }
            }
        }
    }

    fun setLocale(locale: Locale) {
        if (Locale.getDefault() == locale) {
            return
        }

        saveLanguage(this, locale.language)
        finish()
        startActivity(intent)
    }
}