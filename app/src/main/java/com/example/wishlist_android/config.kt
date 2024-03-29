package com.example.wishlist_android

import io.github.cdimascio.dotenv.dotenv

data class ApiConfig(val baseURL: String)


data class Keys(val sharedPreferencesKey: String, val tokenKey: String, val langKey: String)

data class Config(
    val api: ApiConfig,
    val keys: Keys,
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