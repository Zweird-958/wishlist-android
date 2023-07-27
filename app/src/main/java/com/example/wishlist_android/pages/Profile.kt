package com.example.wishlist_android.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity
import com.example.wishlist_android.R
import com.example.wishlist_android.components.Drawer
import com.example.wishlist_android.components.RoundedButton
import com.example.wishlist_android.config
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.saveToken
import com.example.wishlist_android.wishlist
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(navController: NavController) {
    val activity = LocalContext.current as MainActivity
    var isMenuExpanded by remember { mutableStateOf(false) }
    val languagesDisplayed = config.languagesDisplayed
    val currentLanguageFlag = languagesDisplayed[Locale.getDefault().language]

    Drawer(title = stringResource(R.string.profile), navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(modifier = Modifier.padding(vertical = 16.dp)) {

                RoundedButton(
                    modifier = Modifier
                        .width(300.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    onSubmit = { isMenuExpanded = !isMenuExpanded },
                ) {
                    Text(
                        text = "${currentLanguageFlag?.get("flag")} - ${currentLanguageFlag?.get("label")}",
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }


                DropdownMenu(
                    modifier = Modifier
                        .width(300.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                ) {

                    for (language in languagesDisplayed.keys) {
                        val lang = languagesDisplayed[language]
                        DropdownMenuItem(
                            onClick = {
                                isMenuExpanded = false
                                activity.setLocale(Locale(language))
                            }
                        ) {
                            Text(
                                text = "${lang?.get("flag")} - ${lang?.get("label")}",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            RoundedButton(
                modifier = Modifier.width(300.dp),
                onSubmit = {
                    wishlist.clear()
                    navigateAndClearHistory(navController, "signIn", "profile")
                    token = ""
                    saveToken(navController.context, null)
                },
            ) {
                Text(text = stringResource(R.string.sign_out))
            }


        }
    }
}