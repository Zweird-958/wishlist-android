package com.example.wishlist_android.utils

import android.widget.Toast
import androidx.navigation.NavController
import org.json.JSONObject
import retrofit2.Response

fun handleErrors(response: Response<*>, navController: NavController, currentRoute: String) {

    val status = response.code()
    val errorMessage = response.errorBody()?.string()
    val error = JSONObject(errorMessage!!).getString("error")

    Toast.makeText(navController.context, error, Toast.LENGTH_SHORT).show()

    if (status == 403) {
        navigateAndClearHistory(navController, "signIn", currentRoute)
    }

}