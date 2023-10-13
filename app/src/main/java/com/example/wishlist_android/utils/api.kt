package com.example.wishlist_android.utils

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.wishlist_android.api.classes.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> api(
    response: suspend () -> Response<ApiResponse<T>>,
    context: Context,
    navController: NavController,
    currentRoute: String,
    goToRetry: Boolean = false,
): ApiResponse<T> {
    return withContext(Dispatchers.Main) {
        try {
            val response = response()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                handleErrors(response, navController, currentRoute)
                ApiResponse(null, "error")
            }
        } catch (e: Exception) {
            Log.d("error", e.toString())
            handleErrors(e, navController, context, goToRetry)
            ApiResponse(null, "error")
        }
    }
}