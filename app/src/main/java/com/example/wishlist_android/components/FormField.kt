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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val value = remember { mutableStateOf(initialValue) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = {
                value.value = it
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