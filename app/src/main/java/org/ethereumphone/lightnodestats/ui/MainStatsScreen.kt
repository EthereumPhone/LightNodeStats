package org.ethereumphone.lightnodestats.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaquimverges.helium.compose.AppBlock
import com.joaquimverges.helium.core.retained.getRetainedLogicBlock
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ethereumphone.lightnodestats.data.toHumanNumber
import org.ethereumphone.lightnodestats.logic.StatsLogic
import org.ethereumphone.lightnodestats.ui.components.Block
import org.ethereumphone.lightnodestats.ui.components.Status
import org.ethereumphone.lightnodestats.ui.components.StatusInfo
import org.ethereumphone.lightnodestats.ui.components.Tinystatus
import org.ethereumphone.lightnodestats.ui.theme.black
import org.ethereumphone.lightnodestats.ui.theme.ethOSTheme
import org.ethereumphone.lightnodestats.ui.theme.white

@ExperimentalFoundationApi
@Composable
fun MainStatsScreen(){//context: Context) {
    ethOSTheme() {
        Box(modifier = Modifier.background(black).fillMaxSize()){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    "Lightnode",
                    style = MaterialTheme.typography.h2,
                    fontWeight = FontWeight.Bold,
                    color = white
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    "Your personal gateway to Ethereum",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(Modifier.height(24.dp))
                Status(false)
                Spacer(Modifier.height(16.dp))

                /* Examples Blocks*/
                val s1 = StatusInfo("Network", "Ethereum Mainnet")
                val s2 = StatusInfo("Client","Nimbus Verify Proxy")
                //List<StatusInfo> tmp = listOf(s1,s2)
                val list = listOf(s1,s2)

                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    content = {
                        items(list.size) { i ->
                            if(i == list.size-1){
                                Tinystatus(list[i].label,list[i].text,
                                    Modifier
                                        .padding(start = 8.dp)
                                        .clip(MaterialTheme.shapes.small))
                            } else{
                                Tinystatus(list[i].label,list[i].text,
                                    Modifier
                                        .padding(end = 8.dp)
                                        .clip(MaterialTheme.shapes.small))
                            }
                        }

                    }
                )
                Spacer(Modifier.height(36.dp))
                Text(
                    "Block Watcher",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = white
                )
                Block(15949919,189,23)


            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    MainStatsScreen()
}
