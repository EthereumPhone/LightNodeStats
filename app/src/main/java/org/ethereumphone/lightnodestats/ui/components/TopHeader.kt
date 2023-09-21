package org.ethereumphone.lightnodestats.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopHeader(
    name: String,
    icon: @Composable () -> Unit,
) {


    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Address
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ){
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Information",
                    tint = Color.Transparent,
                    modifier = Modifier
                        .clip(CircleShape)
                    //.background(Color.Red)
                )
            }
            Box(
                modifier = Modifier
                ,
            ){
                Text(
                    modifier = Modifier,
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            icon()
        }
        //Networkpill


    }
}

@ExperimentalComposeUiApi
@Composable @Preview
fun PreviewTopHeaderScreen() {
    ///NftCreatorTheme {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    TopHeader(
        "Lightnode",
        icon = {
            IconButton(
                onClick = {}
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
        },

        )
    //}
}