package com.roys.wolvnotekmp.presentation.note.checklist.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.roys.wolvnotekmp.domain.model.CheckListItem
import com.roys.wolvnotekmp.presentation.util.composableicon.SaveIcon
import com.roys.wolvnotekmp.presentation.util.composableicon.SendIcon
import org.jetbrains.compose.resources.stringResource
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.add_item
import wolvnotekmp.composeapp.generated.resources.input_checklist
import wolvnotekmp.composeapp.generated.resources.save

@Composable
fun AddItemView(
    list: List<CheckListItem>,
    onClick: (String, Boolean) -> Unit,
    insertNoteClick: () -> Unit
) {
    val addItemText = stringResource(Res.string.add_item)
    val inputText = stringResource(Res.string.input_checklist)
    val save = stringResource(Res.string.save)
    var checked by rememberSaveable { mutableStateOf(false) }
    var itemText by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.secondary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                )
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = itemText,
                onValueChange = {
                    itemText = it
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions {
                    onClick(itemText, checked)
                    itemText = ""
                    checked = false
                },
                label = {
                    Text(
                        text = inputText,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = itemText.isNotBlank()
                    ) {
                        IconButton(
                            onClick = {
                                onClick(itemText, checked)
                                itemText = ""
                                checked = false
                            }
                        ) {
                            Icon(
                                imageVector = SendIcon(),
                                contentDescription = addItemText,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
            if (list.isNotEmpty()) {
                IconButton(
                    onClick = {
                        insertNoteClick()
                    }
                ) {
                    Icon(
                        imageVector = SaveIcon(),
                        contentDescription = save,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}