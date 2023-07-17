import android.util.Log
import android.widget.Toast
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
import com.example.wishlist_android.api.classes.LoginRequest
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
                            LoginRequest(
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
                        Log.d("ayush: ", e.toString())
                    } finally {
                        isLoading = false
                    }
                }

            }

        },
        buttonTitle = stringResource(R.string.sign_up),
        title = stringResource(R.string.sign_up_title),
        isLoading = isLoading,
    ) {

        val annotatedString = buildAnnotatedString {
            append(stringResource(R.string.already_account))
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(" ${stringResource(R.string.click_sign_in)}")
            }
        }



        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable {
                    navigateAndClearHistory(navController, "signIn", "signUp")

                },
        )
    }

}