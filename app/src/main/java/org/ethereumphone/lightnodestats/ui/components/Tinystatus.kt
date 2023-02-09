package org.ethereumphone.lightnodestats.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import okio.`-DeprecatedUtf8`.size
import org.ethereumphone.lightnodestats.ui.theme.*

@Composable
fun Tinystatus(
    label: String,
    text: String,
    mod: Modifier
) {

    Surface(
        elevation = 12.dp,
        modifier = mod
            //.clip(MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = Modifier
                //.clip(MaterialTheme.shapes.small)
                .background(Color(0xFF2C2C2C))
                .padding(12.dp)

        ){
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.h5,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = white,
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.button,
                    color = gray

                )
            }
        }
    }

}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewTinystatus(
) {

    val s1 = StatusInfo("Network", "Ethereum Mainnet")
    val s2 = StatusInfo("Client","Nimbus Verify Proxy")
    //List<StatusInfo> tmp = listOf(s1,s2)
    val list = listOf(s1,s2)
    /*Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ){
        Tinystatus("Network","Ethereum Mainnet")
        Tinystatus("Client","Nimbus Verify Proxy")
    }*/
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(list.size) { i ->
                if(i == list.size-1){
                    Tinystatus(list[i].label,list[i].text,Modifier.padding(start = 4.dp))
                } else{
                    Tinystatus(list[i].label,list[i].text,Modifier.padding(end = 4.dp))
                }

            }

        }
    )

}

