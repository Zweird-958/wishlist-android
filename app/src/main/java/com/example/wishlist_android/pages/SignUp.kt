import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.api.classes.UserFormBody
import com.example.wishlist_android.components.UserBottomRedirection
import com.example.wishlist_android.components.UserForm
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUp(navController: NavController) {
    val context = LocalContext.current
    val successToast =
        Toast.makeText(context, stringResource(R.string.sign_up_success), Toast.LENGTH_SHORT)

    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
    var isLoading by remember { mutableStateOf(false) }

    UserForm(
        onSubmit = { email, password ->

            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main) {
                    try {

                        isLoading = true

                        val response = wishApi.signUp(
                            UserFormBody(
                                email = email,
                                password = password
                            )
                        )

                        if (response.isSuccessful) {
                            navigateAndClearHistory(navController, "signIn", "signUp")
                            successToast.show()
                        } else {
                            handleErrors(response, navController, "signUp")
                        }
                    } catch (e: Exception) {
                        handleErrors(e, navController, context)
                    } finally {
                        isLoading = false
                    }
                }

            }

        },
        buttonTitle = stringResource(R.string.sign_up),
        title = stringResource(R.string.sign_up_title),
        isLoading = isLoading,
    ) {}

    UserBottomRedirection(
        leftText = stringResource(R.string.already_account),
        rightText = stringResource(R.string.click_sign_in),
        navController = navController,
        currentRoute = "signUp",
    )

}