package com.example.wishlist_android.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.classes.DrawerItem
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun Drawer(
    title: String,
    navController: NavController,
    floatingActionButton: @Composable() (() -> Unit?)? = null,
    children: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        },
        content = {
            CustomScaffold(
                title = title,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                floatingActionButton = {
                    if (floatingActionButton != null) {
                        floatingActionButton()
                    }
                },
            ) {
                children()
            }
        }
    )
}

@Composable
fun DrawerContent(
    navController: NavController,
) {
    val drawerItems = listOf(
        DrawerItem(stringResource(R.string.wishlist), "wishlist", Icons.Default.Favorite),
        DrawerItem(stringResource(R.string.profile), "profile", Icons.Default.AccountCircle),
    )

    ModalDrawerSheet(
        modifier = Modifier.width(200.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        content = {
            LazyColumn(modifier = Modifier.padding(6.dp)) {
                items(drawerItems.size) { index ->
                    val drawerItem = drawerItems[index]
                    val currentRoute = navController.currentDestination?.route
                    val isDrawerRoute = drawerItems.any { it.route == currentRoute }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {
                                if (isDrawerRoute) {
                                    navigateAndClearHistory(
                                        navController,
                                        drawerItem.route,
                                        currentRoute!!
                                    )
                                } else {
                                    navController.navigate(drawerItem.route) {
                                        launchSingleTop = true
                                    }
                                }
                            },
                    ) {
                        Icon(
                            imageVector = drawerItem.icon,
                            contentDescription = drawerItem.label,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = drawerItem.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                }
            }

        }
    )
}