package com.roys.wolvnotekmp.presentation.note.draw.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roys.wolvnotekmp.presentation.note.draw.DrawEvent
import com.roys.wolvnotekmp.domain.model.PathData
import com.roys.wolvnotekmp.presentation.note.component.IconTextButton
import com.roys.wolvnotekmp.presentation.note.component.TextButton
import com.roys.wolvnotekmp.presentation.util.composableicon.CleanIcon
import com.roys.wolvnotekmp.presentation.util.composableicon.GalleryIcon
import com.roys.wolvnotekmp.presentation.util.composableicon.SaveIcon
import org.jetbrains.compose.resources.stringResource
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.clear
import wolvnotekmp.composeapp.generated.resources.save
import wolvnotekmp.composeapp.generated.resources.save_and_export

@Composable
fun DrawMenu(
    paths: List<PathData>,
    selectedColor: Color,
    colors: List<Color>,
    selectedWeight: Float,
    weights: List<Float>,
    onClick: (DrawEvent) -> Unit,
    onSelectColor: (Color) -> Unit,
    onSelectWeight: (Float) -> Unit
){
    val clear = stringResource(Res.string.clear)
    val save = stringResource(Res.string.save)
    val saveExport = stringResource(Res.string.save_and_export)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column {
            SelectWeightComponent(
                selectedWeight = selectedWeight,
                weights = weights,
                onSelectWeight = { onSelectWeight(it) }
            )
            SelectColorComponent(
                colors = colors,
                selectedColor = selectedColor,
                onSelectColor = { onSelectColor(it) }
            )
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                IconTextButton(
                    { onClick(DrawEvent.OnClearCanvasClick) },
                    clear,
                    CleanIcon()
                )
                Spacer(modifier = Modifier.weight(1f))
                AnimatedVisibility(
                    visible = paths.isNotEmpty()
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 6.dp),
                            onClick = {onClick(DrawEvent.InsertNote)}
                        ) {
                            Icon(
                                imageVector = SaveIcon(),
                                contentDescription = save,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        TextButton(
                            imageVector = GalleryIcon(),
                            label = saveExport,
                            contentColor = MaterialTheme.colorScheme.primary,
                            onClick = {onClick(DrawEvent.InsertAndExport)}
                        )
                    }
                }
            }
        }
    }
}