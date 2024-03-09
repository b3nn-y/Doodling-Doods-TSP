package com.game.doodlingdoods.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game.doodlingdoods.R
import com.game.doodlingdoods.data.KtorRealtimeCommunicationClient
import com.game.doodlingdoods.data.RealtimeCommunicationClient
import com.game.doodlingdoods.viewmodels.DrawingScreenViewModel
import com.game.doodlingdoods.viewmodels.ServerCommunicationViewModel
import io.ktor.client.HttpClient

@Composable
fun ChooseWordScreen(serverViewModel: ServerCommunicationViewModel) {

    val drawingScreenViewModel = viewModel<DrawingScreenViewModel>()

    ChooseWord(words = arrayListOf("name","age,","gender")
        ,serverViewModel=serverViewModel ,
        wordsOnclick = { //TODO need to update this line
            drawingScreenViewModel._isBottomSheetOpen.value = false
        })
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ChooseWord(
    words:ArrayList<String>,
    serverViewModel:ServerCommunicationViewModel,
    wordsOnclick:()->Unit,
    modifier: Modifier=Modifier
    ) {
    Scaffold(
        modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.choose_word_background),
            contentDescription ="bg image" ,
            modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
            )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(8F),
            contentAlignment = Alignment.Center
        ) {


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp) // Adjust the spacing as needed
            ) {
                items(words) { word ->
                    Text(
                        text = word,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp) // Adjust the height as needed for visibility
                            .clickable {
                                // Handle the click action here
                                wordsOnclick()
                                serverViewModel.sendWord(word)


                                println("Clicked on word: $word")
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp, // Adjust the font size as needed
                    )
                }
            }
        }

    }
}

//@Preview
//@Composable
//fun PreviewChooseWord() {
//    val serverViewModel =ServerCommunicationViewModel(KtorRealtimeCommunicationClient(HttpClient()))
//    val drawingScreenViewModel = viewModel<DrawingScreenViewModel>()
//
//    ChooseWord(words = arrayListOf("name","age,","gender")
//        ,serverViewModel=serverViewModel ,
//        wordsOnclick = { //TODO need to update this line
//            drawingScreenViewModel._isBottomSheetOpen.value = false
//        }
//    )
//}


@Composable
fun ChooseCard(
    modifier: Modifier=Modifier,
    word:String
) {
    Card(
        modifier
            .fillMaxWidth()
    ) {
        Text(text =word )
    }
}
@Preview
@Composable
fun CardPreview() {
    ChooseCard(word = "Name")
}
