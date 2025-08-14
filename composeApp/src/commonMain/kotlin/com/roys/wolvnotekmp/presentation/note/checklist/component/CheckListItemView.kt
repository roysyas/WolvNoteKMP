package com.roys.wolvnotekmp.presentation.note.checklist.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roys.wolvnotekmp.domain.model.CheckListItem
import com.roys.wolvnotekmp.presentation.util.composableicon.CloseIcon
import com.roys.wolvnotekmp.presentation.util.composableicon.DeleteIcon
import org.jetbrains.compose.resources.stringResource
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.delete_list_item

@Composable
fun CheckListItemView(
    checkListItem: CheckListItem,
    onRemoveClick: () -> Unit,
    onCheckedChange: () -> Unit
){
    val deleteListItem = stringResource(Res.string.delete_list_item)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkListItem.checked,
                onCheckedChange = {
                    onCheckedChange()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.secondary,
                    uncheckedColor = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = checkListItem.text,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onRemoveClick()
                }
            ) {
                Icon(
                    imageVector = DeleteIcon(),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = deleteListItem
                )
            }
        }
    }
}