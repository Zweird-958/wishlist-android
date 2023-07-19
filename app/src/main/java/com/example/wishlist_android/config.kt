package com.example.wishlist_android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import com.example.wishlist_android.classes.DrawerItem
import io.github.cdimascio.dotenv.dotenv

data class ApiConfig(val baseURL: String)

data class SessionConfig(val tokenKey: String, val sharedPreferencesKey: String)

data class DrawerConfig(val items: List<DrawerItem>)

data class Config(val api: ApiConfig, val session: SessionConfig, val drawerItems: List<DrawerItem>)

val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

val config = Config(
    api = ApiConfig(
        baseURL = dotenv["API_BASE_URL"]
    ),
    session = SessionConfig(
        tokenKey = "token",
        sharedPreferencesKey = "MyPreferences",
    ),
    drawerItems = listOf(
        DrawerItem("My Wishlist", "wishlist", Icons.Default.Favorite),
        DrawerItem("Profile", "profile", Icons.Default.AccountCircle),
    )
)