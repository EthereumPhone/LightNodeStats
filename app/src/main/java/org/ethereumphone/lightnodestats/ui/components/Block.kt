package org.ethereumphone.lightnodestats.ui.components
import org.ethereumphone.lightnodestats.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ethereumphone.lightnodestats.ui.theme.gray
import org.ethereumphone.lightnodestats.ui.theme.white
import java.math.BigInteger

//@Preview
@Composable
fun Block(
    number: String,
    tx: String,
    gas: String,
    onClick: () -> Unit
) {

    Box(
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment= Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {

            Text(
                text = ""+number,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.weight(.5f),
                textAlign = TextAlign.Start

            )
            Text(
                text = tx,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color(0xFFC8C8C8),
                modifier = Modifier.weight(.3f),
                textAlign = TextAlign.Center

            )
            Text(text = gas,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color(0xFFC8C8C8),
                modifier = Modifier.weight(.3f),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(32.dp)
            ) {
                Box(
                    modifier = Modifier.weight(.1f),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Block Details", tint = Color.White)
                }
            }


        }
    }



}

@Preview(showBackground = true)
@Composable
fun PreviewBlock(
) {
    Column(
        modifier = Modifier.background(Color.Red)
    ) {
        Block("15949919","189", "0.13165") {

        }
    }

}