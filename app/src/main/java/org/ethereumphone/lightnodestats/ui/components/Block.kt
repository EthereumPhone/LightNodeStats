package org.ethereumphone.lightnodestats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ethereumphone.lightnodestats.ui.theme.gray
import org.ethereumphone.lightnodestats.ui.theme.white
import java.math.BigInteger

@Composable
fun Block(
    number: BigInteger,
    transactions: Int,
    gas: String,

    ) {
    Box(
        modifier = Modifier
            //.background(Color(0xFF2C2C2C))
            .padding(12.dp)
            .fillMaxWidth()

    ){
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Block "+number,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = white
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(){
                Text(
                    text = "Transaction ",
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Normal,
                    color = gray

                )
                Text(
                    text = ""+transactions,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,
                    color = gray

                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Gas ",
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Normal,
                    color = gray

                )
                Text(
                    text = gas+"".uppercase(),
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,
                    color = gray

                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBlock(
) {
    Block(BigInteger("15949919"),189,"23")
}
