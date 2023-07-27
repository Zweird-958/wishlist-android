import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wishlist_android.components.LoaderRoundedButton

@Composable
fun Form(
    title: String,
    buttonTitle: String,
    onSubmit: () -> Unit,
    buttonEnabled: Boolean = true,
    isLoading: Boolean = false,
    children: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        children()
        Spacer(modifier = Modifier.height(16.dp))
        LoaderRoundedButton(onSubmit = onSubmit, loading = isLoading, isEnabled = buttonEnabled) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = buttonTitle,
                )
            }
        }
    }
}