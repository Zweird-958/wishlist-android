import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.example.wishlist_android.R
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.api.classes.UserFormBody
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
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
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
                                token = result
                                navigateAndClearHistory(navController, "wishlist", "signIn")
                            }
                        } else {
                            handleErrors(response, navController, "signIn")
                        }

                    } catch (e: Exception) {
                        handleErrors(e, navController, "signIn", context)
                    } finally {
                        isLoading = false
                    }
                }
            }
        }
    ) {
        val annotatedString = buildAnnotatedString {
            append(stringResource(R.string.new_account))
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(" ${stringResource(R.string.click_sign_up)}")
            }
        }


        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable {
                    navigateAndClearHistory(navController, "signUp", "signIn")
                },
        )


    }
}