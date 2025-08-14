package com.roys.wolvnotekmp.presentation.note.mainpage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roys.wolvnotekmp.domain.model.CheckListItem
import kotlinx.serialization.json.Json

@Composable
fun CheckListComponent(
    modifier: Modifier,
    noteContent: String,
    noteTitle: String
) {
    var data by remember { mutableStateOf(listOf<CheckListItem>()) }
    data = Json.decodeFromString(noteContent)

    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier.padding(4.dp)
    ) {
        Column{
            Column(
                modifier = modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                data.forEachIndexed { index, item ->
                    if (index < 5) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Checkbox(
                                checked = item.checked,
                                onCheckedChange = {},
                                enabled = false,
                                colors = CheckboxDefaults.colors(
                                    disabledUncheckedColor = MaterialTheme.colorScheme.primary,
                                    disabledCheckedColor = MaterialTheme.colorScheme.primary,
                                    checkmarkColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                            Text(
                                modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp),
                                text = item.text,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                if (data.size > 5) {
                    Text(
                        text = "...",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
            NoteTitleItemComponent(noteTitle)
        }
    }
}