package com.game.doodlingdoods.screens
//
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.game.doodlingdoods.R
//
//@Composable
//fun DisableOnClickEffectExample() {
//    // Create a custom empty interaction source
//    val interactionSource = remember { MutableInteractionSource() }
//
//    Surface(
//        modifier = Modifier
//
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        // Use the custom interaction source for the Image
//        Image(
//            painter = painterResource(id = R.drawable.sign_up_button),
//            contentDescription = null,
//            modifier = Modifier
//                .size(200.dp)
//                .clickable(
//                    interactionSource = interactionSource,
//                    indication = null
//                ) {
//                    // Your onClick logic here (optional)
//                }
//        )
//    }
//}
//
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewDis() {
//    DisableOnClickEffectExample()
//}