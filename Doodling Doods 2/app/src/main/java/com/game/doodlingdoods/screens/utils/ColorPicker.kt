package com.game.doodlingdoods.screens.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker() {
    var colorEnvelope by rememberSaveable {
        mutableStateOf<ColorEnvelope?>(null)
    }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    if (isSheetOpen){
        ModalBottomSheet(sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            }) {

            val controller = rememberColorPickerController()

            Column (modifier = Modifier.padding(10.dp)){
                Row (modifier = Modifier.fillMaxWidth()) {
                    AlphaTile(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                        controller = controller
                    )
                }
                HsvColorPicker(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp),
                    controller = controller,
                    onColorChanged = {color ->
                        colorEnvelope = color
                    }
                )
                AlphaSlider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(30.dp),
                    controller = controller
                )
                BrightnessSlider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(30.dp),
                    controller = controller
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewTest() {
    ColorPicker()
}