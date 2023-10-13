import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity.Companion.wishApi
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.UserFormBody
import com.example.wishlist_android.components.UserBottomRedirection
import com.example.wishlist_android.components.UserForm
import com.example.wishlist_android.utils.api
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.saveToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    UserForm(
        title = stringResource(R.string.sign_in_title),
        buttonTitle = stringResource(R.string.sign_in),
        isLoading = isLoading,
        onSubmit = { email, password, _ ->
            scope.launch {
                isLoading = true

                val response = api(
                    response = { wishApi.signIn(UserFormBody(email, password)) },
                    context = context,
                    navController = navController,
                    goToRetry = true,
                    currentRoute = "signIn"
                )

                val result = response.result
                if (result != null) {
                    saveToken(context, result)
                    navigateAndClearHistory(navController, "wishlist", "signIn")
                }

                isLoading = false
            }
        }
    ) {}

    UserBottomRedirection(
        leftText = stringResource(R.string.new_account),
        rightText = stringResource(R.string.click_sign_up),
        navController = navController,
        currentRoute = "signIn",
    )


}