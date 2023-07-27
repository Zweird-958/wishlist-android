package com.example.wishlist_android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import com.example.wishlist_android.classes.DrawerItem
import io.github.cdimascio.dotenv.dotenv

data class ApiConfig(val baseURL: String)


data class Keys(val sharedPreferencesKey: String, val tokenKey: String, val langKey: String)

data class Config(
    val api: ApiConfig,
    val keys: Keys,
    val drawerItems: List<DrawerItem>,
    val defaultLanguage: String,
    val languagesDisplayed: Map<String, Map<String, String>>
)

val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

val config = Config(
    api = ApiConfig(
        baseURL = dotenv["API_BASE_URL"]
    ),
    keys = Keys(
        sharedPreferencesKey = "MyPreferences",
        tokenKey = "token",
        langKey = "lang"
    ),
    drawerItems = listOf(
        DrawerItem("My Wishlist", "wishlist", Icons.Default.Favorite),
        DrawerItem("Profile", "profile", Icons.Default.AccountCircle),
    ),
    defaultLanguage = "en",
    languagesDisplayed = mapOf(
        "en" to mapOf<String, String>(
            "label" to "English",
            "flag" to "🇬🇧"
        ),
        "fr" to mapOf<String, String>(
            "label" to "Français",
            "flag" to "🇫🇷"
        ),
    )
)