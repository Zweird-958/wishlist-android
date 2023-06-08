import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    label: String,
    initialValue: String,
    onValueChange: (String) -> Unit
) {
    val value = remember { mutableStateOf(initialValue) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value.value,
        onValueChange = {
            value.value = it
            onValueChange(it)
        },
        label = { Text(label) }
    )
}