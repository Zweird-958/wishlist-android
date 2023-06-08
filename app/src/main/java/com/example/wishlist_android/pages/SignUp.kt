import androidx.compose.runtime.Composable
import com.example.wishlist_android.components.UserForm
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUp() {
    UserForm(
        title = "Connexion",
        buttonTitle = "Se connecter",
        onSubmit = {


        }
    )
}