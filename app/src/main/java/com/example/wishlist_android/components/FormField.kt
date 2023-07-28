import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wishlist_android.components.ErrorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    modifier: Modifier = Modifier,
    label: String,
    initialValue: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = initialValue,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = {
                onValueChange(it)
            },
            isError = error != null,
            label = { Text(label) }
        )

        if (error != null) {
            ErrorText(error = error)
        }
    }


}