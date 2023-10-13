import android.widget.Toast
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
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val successToast =
        Toast.makeText(context, stringResource(R.string.sign_up_success), Toast.LENGTH_SHORT)

    var isLoading by remember { mutableStateOf(false) }

    UserForm(
        onSubmit = { email, password, username ->
            scope.launch {
                isLoading = true
                val response = api(
                    response = { wishApi.signUp(UserFormBody(email, password, username)) },
                    context = context,
                    navController = navController,
                    goToRetry = true,
                    currentRoute = "signUp"
                )

                val result = response.result

                if (result != null) {
                    navigateAndClearHistory(navController, "signIn", "signUp")
                    successToast.show()

                }

                isLoading = false

            }

        },
        buttonTitle = stringResource(R.string.sign_up),
        title = stringResource(R.string.sign_up_title),
        isLoading = isLoading,
        showUsername = true,
    ) {}

    UserBottomRedirection(
        leftText = stringResource(R.string.already_account),
        rightText = stringResource(R.string.click_sign_in),
        navController = navController,
        currentRoute = "signUp",
    )

}