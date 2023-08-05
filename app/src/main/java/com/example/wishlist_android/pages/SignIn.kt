import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.MainActivity
import com.example.wishlist_android.R
import com.example.wishlist_android.api.classes.UserFormBody
import com.example.wishlist_android.components.UserBottomRedirection
import com.example.wishlist_android.components.UserForm
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.saveToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val wishApi = MainActivity.wishApi
    var isLoading by remember { mutableStateOf(false) }

    UserForm(
        title = stringResource(R.string.sign_in_title),
        buttonTitle = stringResource(R.string.sign_in),
        isLoading = isLoading,
        onSubmit = { email, password ->

            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main) {

                    try {
                        isLoading = true

                        val response = wishApi.signIn(
                            UserFormBody(
                                email = email,
                                password = password
                            )
                        )

                        if (response.isSuccessful) {
                            val result = response.body()?.result
                            if (result != null) {
                                saveToken(context, result)
                                navigateAndClearHistory(navController, "wishlist", "signIn")
                            }
                        } else {
                            handleErrors(response, navController, "signIn")
                        }

                    } catch (e: Exception) {
                        handleErrors(e, navController, context)
                    } finally {
                        isLoading = false
                    }
                }
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