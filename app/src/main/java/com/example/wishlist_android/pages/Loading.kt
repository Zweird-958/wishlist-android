package com.example.wishlist_android.pages

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.getToken
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.wishlist
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun LoadingPage(navController: NavController) {
    val context = LocalContext.current
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
    LaunchedEffect(Unit) {
        val tokenLoaded = getToken(context = context)

        if (tokenLoaded != null) {

            token = tokenLoaded

            GlobalScope.launch {

                try {

                    val response = wishApi.getWish(token)

                    if (response.isSuccessful) {
                        val result = response.body()?.result

                        if (result != null) {
                            wishlist = result
                        }

                        withContext(Dispatchers.Main) {
                            navigateAndClearHistory(navController, "wishlist", "loading")
                        }
                    } else {

                        Looper.prepare()
                        handleErrors(response, navController, "loading")

                    }

                    Log.d("LoadingPage", "response: $response")
                } catch (e: Exception) {
                    println(e)
                }
            }

//            navigateAndClearHistory(navController, "signUp", "loading")
        } else {
            navigateAndClearHistory(navController, "signIn", "loading")
        }
    }


    Scaffold(
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingCircle()
            }
        }
    )
}

@Composable
fun LoadingCircle() {
    var rotationAngle by remember { mutableStateOf(0f) }
    val primaryColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(Unit) {
        while (true) {
            delay(16L) // Adjust the delay to control the speed of rotation
            rotationAngle += 5f
        }
    }

    Canvas(
        modifier = Modifier
            .size(40.dp)
            .graphicsLayer(
                rotationZ = rotationAngle
            )
    ) {
        val strokeWidth = 5f

        val gradientBrush = Brush.sweepGradient(
            listOf(primaryColor, primaryColor),
            center
        )

        drawArc(
            brush = gradientBrush,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
    }
}