import android.os.Looper
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
import com.example.wishlist_android.utils.handleErrors
import com.example.wishlist_android.utils.navigateAndClearHistory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUp(navController: NavController) {
    val context = LocalContext.current
    val successToast =
        Toast.makeText(context, stringResource(R.string.sign_up_success), Toast.LENGTH_SHORT)


    UserForm(
        title = stringResource(R.string.sign_up_title),
        buttonTitle = stringResource(R.string.sign_up),
        onSubmit = { email, password ->

            val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
            GlobalScope.launch {

                try {

                    val response = wishApi.signUp(
                        LoginRequest(
                            email = email,
                            password = password
                        )
                    )

                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            navigateAndClearHistory(navController, "signIn", "signUp")
                        }
                        successToast.show()
                    } else {
                        Looper.prepare()
                        handleErrors(response, navController, "signUp")
                        Looper.loop()
                    }
                } catch (e: Exception) {
                    Log.d("ayush: ", e.toString())
                }

            }

        }
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