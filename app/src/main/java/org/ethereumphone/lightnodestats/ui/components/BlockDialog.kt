package org.ethereumphone.lightnodestats.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import org.web3j.protocol.core.methods.response.EthBlock

@Composable
fun BlockDialog(
    currentBlockToShow: MutableState<EthBlock.Block?>,
    setShowDialog: () -> Unit,
    uiContext: Context
){

    Dialog(
        onDismissRequest = { setShowDialog() },
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Black,
            contentColor = Color.White,
            border = BorderStroke(width = 1.dp, Color.White),
            elevation = 2.dp
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    if (currentBlockToShow.value != null) {//currentBlockToShow.value != null
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "",
                                tint = Color.Transparent,
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                            )
                            Text(
                                text = "Block Info",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close",
                                tint = Color(0xFF9FA2A5),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { setShowDialog() }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        //Block number
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Block number",
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = currentBlockToShow.value!!.number.toString(),
                                fontSize = 16.sp,
                                color = Color(0xFF9FA2A5),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                            )
                        }


                        //Timestamp
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Timestamp",
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.SemiBold,
                            )

                            Text(
                                text = currentBlockToShow.value!!.timestamp.toString(),
                                fontSize = 16.sp,
                                color = Color(0xFF9FA2A5),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                            )


                        }

                        //Validator
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Validator",
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(

                                text = currentBlockToShow.value!!.miner.substring(
                                    0,
                                    5
                                ) + "..." + currentBlockToShow.value!!.miner.substring(
                                    currentBlockToShow.value!!.miner.length - 3,
                                    currentBlockToShow.value!!.miner.length
                                ),
                                fontSize = 16.sp,
                                color = Color(0xFF9FA2A5),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                            )
                        }


                        //Gas used
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Gas used",
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = currentBlockToShow.value!!.gasUsed.toString(),
                                fontSize = 16.sp,
                                color = Color(0xFF9FA2A5),
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                            )
                        }



                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            EtherscanButton(
                                text = "Open in etherscan",
                                onClickChange = {
                                    //Log.e("tag","URL IS "+url.value.text)
                                    val urlIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            "https://etherscan.io/block/${currentBlockToShow.value!!.number}"
                                        )
                                    )
                                    uiContext.startActivity(urlIntent)

                                }
                            )

                        }

                }




                }
            }
        }
    }
}


@Composable
fun EtherscanButton(
    onClickChange: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClickChange,
        contentPadding = PaddingValues(14.dp,0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,//1E2730),
            contentColor = Color.White
        ),
        enabled = true,
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
        )
        //modifier = modifier.padding(14.dp, 0.dp),
    ) {

        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF71B5FF),
            modifier = modifier.padding(0.dp),
        )

    }
}

@Preview
@Composable
fun PreviewDialogScreen() {
//    BlockDialog(
//
//        setShowDialog = {},
//        uiContext = LocalContext.current
//    )
}



//class MockBlock(
//    val blocknumber: String,
//    val timestamp: String,
//    val miner: String,
//    val gasused: String
//)
