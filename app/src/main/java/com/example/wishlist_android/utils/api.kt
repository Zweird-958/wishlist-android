package com.example.wishlist_android.utils

import android.content.Context
import androidx.navigation.NavController
import com.example.wishlist_android.api.classes.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> api(
    response: Response<ApiResponse<T>>,
    context: Context,
    navController: NavController,
    currentRoute: String,
): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            if (response.isSuccessful) {
                response.body()!!
            } else {
                handleErrors(response, navController, currentRoute)
                ApiResponse(null, "error")
            }

        } catch (e: Exception) {
            handleErrors(e, navController, context)
            ApiResponse(null, "error")

        }
    }
}