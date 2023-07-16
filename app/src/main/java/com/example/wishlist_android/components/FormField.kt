import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    label: String,
    initialValue: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: String? = null,
) {
    val value = remember { mutableStateOf(initialValue) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            ),
            keyboardOptions = keyboardOptions,
            onValueChange = {
                value.value = it
                onValueChange(it)
            },
            isError = error != null,
            label = { Text(label) }
        )

        if (error != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }
    }


}