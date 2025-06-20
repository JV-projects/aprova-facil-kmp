package edu.fatec.jvprojects.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.fatec.jvprojects.utils.extensions.convertMilisToDate

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    dataSelecionada: Long?,
    modalOpen: Boolean,
    label: String
) {
    Column(modifier = modifier.padding(5.dp),  horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = modifier, text = label, textAlign = TextAlign.Center)
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(dataSelecionada?.convertMilisToDate() ?: "", textAlign = TextAlign.Center)
            Button(
                onClick = onDismiss
            ) {
                Text("Escolher data")
            }

            if (modalOpen) {
                DatePickerModal(onDateSelected, onDismiss)
            }
        }
    }
}