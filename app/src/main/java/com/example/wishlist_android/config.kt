package com.example.wishlist_android

import io.github.cdimascio.dotenv.dotenv

data class ApiConfig(val baseURL: String)

data class SessionConfig(val localStorageKey: String)

data class Config(val api: ApiConfig, val session: SessionConfig)

val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

val config = Config(
    api = ApiConfig(
        baseURL = dotenv["API_BASE_URL"]
    ),
    session = SessionConfig(
        localStorageKey = "token"
    )
)