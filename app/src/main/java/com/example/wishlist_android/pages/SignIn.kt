import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.navigateAndClearHistory
import com.example.wishlist_android.utils.saveToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val toast =
        Toast.makeText(context, stringResource(R.string.invalid_credentials), Toast.LENGTH_SHORT)
    val timeoutToast =
        Toast.makeText(context, stringResource(R.string.api_error), Toast.LENGTH_SHORT)
    val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)

    UserForm(
        title = stringResource(R.string.sign_in_title),
        buttonTitle = stringResource(R.string.sign_in),
        onSubmit = { email, password ->

            GlobalScope.launch {

                try {

                    val response = wishApi.signIn(
                        LoginRequest(
                            email = email,
                            password = password
                        )
                    )


                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        if (result != null) {
                            saveToken(context, result)
                            token = result
                        }
                    } else {

                        // Get Error

                        val status = response.code()

                        if (status == 401) {
                            Log.d("ayush: ", "401")
                            toast.show()
                        }

//                        response.errorBody()?.string()
                    }
                } catch (e: Exception) {
                    Log.d("error: ", e.toString())
                    timeoutToast.show()
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
            modifier = Modifier.clickable {
                navigateAndClearHistory(navController, "signUp", "signIn")
            },
        )
    }
}