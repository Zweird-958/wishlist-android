package com.example.wishlist_android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.wishlist_android.R
import org.json.JSONObject
import retrofit2.Response

fun handleErrors(response: Response<*>, navController: NavController, currentRoute: String) {
    val status = response.code()
    val errorMessage = response.errorBody()?.string()
    val error = JSONObject(errorMessage!!).getString("error")

    if (currentRoute == "signUp" && status == 500) {
        navigateAndClearHistory(navController, "signIn", currentRoute)
        return
    }

    Toast.makeText(navController.context, error, Toast.LENGTH_SHORT).show()

    if (status == 403) {
        saveToken(navController.context, null)
        navigateAndClearHistory(navController, "signIn", currentRoute)
    }

}

@SuppressLint("ShowToast")
fun handleErrors(
    error: Exception,
    navController: NavController,
    context: Context,
    goToRetry: Boolean = false
) {
    if (goToRetry) {
        navController.navigate("retry")
        return
    } else {
        Toast.makeText(context, context.getString(R.string.api_error), Toast.LENGTH_SHORT).show()
    }


}