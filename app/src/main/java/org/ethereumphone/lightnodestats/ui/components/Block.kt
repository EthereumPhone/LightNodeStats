package org.ethereumphone.lightnodestats.ui.components
import org.ethereumphone.lightnodestats.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    gas: String
) {

        Box(
            //modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment= Alignment.CenterVertically,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .width(width = 56.dp)
                        .height(height = 56.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cube),
                        contentDescription = "Cube",
                        contentScale = ContentScale.Inside,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(10.dp)
                    )

                }
                Spacer(
                    modifier = Modifier
                        .width(width = 16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ""+number,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White

                    )
                    Row(
                        verticalAlignment= Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_chart),
                            contentDescription = "Chart",
                            contentScale = ContentScale.Inside,
                            colorFilter = ColorFilter.tint(Color(0xFFC8C8C8)),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(width = 8.dp))
                        Text(
                            text = tx,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFFC8C8C8)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(width = 16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_ether),
                            contentDescription = "Ether",
                            contentScale = ContentScale.Inside,
                            colorFilter = ColorFilter.tint(Color(0xFFC8C8C8)),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(width = 8.dp))
                        Text(text = gas,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFFC8C8C8))
                    }
                }


            }
        }



}

@Preview(showBackground = true)
@Composable
fun PreviewBlock(
) {
    Block("15949919","189", "0.13165")
}
