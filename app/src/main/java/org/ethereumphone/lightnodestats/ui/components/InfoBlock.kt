package org.ethereumphone.lightnodestats.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import okio.`-DeprecatedUtf8`.size
import org.ethereumphone.lightnodestats.R
import org.ethereumphone.lightnodestats.ui.theme.*

@Composable
fun InfoBlock(
    modifier: Modifier = Modifier,
    text: String,
    title: String = "",
    hasTitle: Boolean,
    icon: @Composable () -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
    ){
        if(hasTitle){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    //modifier = modifier.padding(start = 16.dp),
                    text = title,
                    fontSize = 16.sp,
                    color = Color(0xFF9FA2A5)
                )
                Spacer(modifier = Modifier.width(8.dp))
                icon()
            }
        }

        Column (
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Transparent)
        ){
            val Inter = FontFamily(
                Font(R.font.inter_light,FontWeight.Light),
                Font(R.font.inter_regular,FontWeight.Normal),
                Font(R.font.inter_medium,FontWeight.Medium),
                Font(R.font.inter_semibold,FontWeight.SemiBold),
                Font(R.font.inter_bold, FontWeight.Bold)
            )
            Text(
                text = text,
                color = Color.White,
                lineHeight = 109.sp,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Inter
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewTinystatus(
) {
    InfoBlock(
        text = "Ethereum mainnet",
        title = "Network",
        hasTitle = true,
        icon = {
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {}//}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Information",
                    tint = Color(0xFF9FA2A5),
                    modifier = Modifier
                        .clip(CircleShape)
                    //.background(Color.Red)
                )
            }
        }
    )
}
/*
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

}*/
