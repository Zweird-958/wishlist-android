import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.wishlist_android.api.RetrofitHelper
import com.example.wishlist_android.api.WishApi
import com.example.wishlist_android.api.classes.LoginRequest
import com.example.wishlist_android.components.UserForm
import com.example.wishlist_android.token
import com.example.wishlist_android.utils.saveToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignIn() {
    val context = LocalContext.current

    UserForm(
        title = "Connexion",
        buttonTitle = "Se connecter",
        onSubmit = { email, password ->

            val wishApi = RetrofitHelper.getInstance().create(WishApi::class.java)
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
                        }

//                        response.errorBody()?.string()
                    }
                } catch (e: Exception) {
                    Log.d("ayush: ", e.toString())
                }

            }

        }
    )
}