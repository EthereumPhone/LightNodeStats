package org.ethereumphone.lightnodestats.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun SwitchBlock(options: Array<String>, onSelectedOption: (String) -> Unit) {
    var expanded =  remember { mutableStateOf(false) }
    var selectedOption =  remember { mutableStateOf(options[0]) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        val Inter = FontFamily(
            Font(R.font.inter_light, FontWeight.Light),
            Font(R.font.inter_regular, FontWeight.Normal),
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_semibold, FontWeight.SemiBold),
            Font(R.font.inter_bold, FontWeight.Bold)
        )

        Box(modifier = Modifier.clickable { expanded.value = !expanded.value }) {
            Text(
                text = selectedOption.value,
                color = Color.White,
                lineHeight = 109.sp,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Inter
                ),
                modifier = Modifier.padding(16.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd) // Align the icon to the end (right) of the box
                    .padding(end = 12.dp)
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption.value = option
                        expanded.value = false
                        onSelectedOption(option)
                    }
                ) {
                    Text(text = option,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Inter
                        ))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSwitchBlock() {
    val options = arrayOf("Nimbus client", "Helios Client")
    SwitchBlock(options = options, onSelectedOption = {})
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

