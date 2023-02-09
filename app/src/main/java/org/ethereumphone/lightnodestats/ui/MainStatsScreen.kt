package org.ethereumphone.lightnodestats.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
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
import org.ethereumphone.lightnodestats.ui.theme.*

@ExperimentalFoundationApi
@Composable
fun MainStatsScreen(context: Context) {
    val context = context
    val logic = LocalContext.current.getRetainedLogicBlock<StatsLogic>()
    logic.pushContext(context)

    ethOSTheme() {
        AppBlock(logic) { state, events ->
            state?.let {
                Box(modifier = Modifier.background(black).fillMaxSize()) {
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

                        //Status(state.isOnline,)//false)
                        //Status
                        Status(context,state,events)
                        //End-Status
                        Spacer(Modifier.height(16.dp))

                        //Info
                        val s1 = StatusInfo("Network", "Ethereum Mainnet")
                        val s2 = StatusInfo("Client", "Nimbus Verify Proxy")
                        val list = listOf(s1, s2)

                        LazyVerticalGrid(
                            cells = GridCells.Fixed(2),
                            content = {
                                items(list.size) { i ->
                                    if (i == list.size - 1) {
                                        Tinystatus(
                                            list[i].label, list[i].text,
                                            Modifier
                                                .padding(start = 8.dp)
                                                .clip(MaterialTheme.shapes.small)
                                        )
                                    } else {
                                        Tinystatus(
                                            list[i].label, list[i].text,
                                            Modifier
                                                .padding(end = 8.dp)
                                                .clip(MaterialTheme.shapes.small)
                                        )
                                    }
                                }

                            }
                        )
                        Spacer(Modifier.height(36.dp))
                        //Block Watcher
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Latest Blocks",
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                color = white
                            )
                            Spacer(Modifier.weight(1f))
                            if (state.isOnline) {
                                if (state.canGetBlocks) {
                                    Text("⛓", style = MaterialTheme.typography.h4)
                                } else {
                                    CircularProgressIndicator(
                                        Modifier
                                            .width(24.dp)
                                            .height(24.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        val scope = rememberCoroutineScope()
                        val listState = rememberLazyListState()
                        LazyColumn(state = listState, reverseLayout = true) {
                            items(state.blocks.size) { index ->
                                val block = state.blocks[index]
                                /*Column(Modifier.fillMaxWidth()) {
                                    Text(
                                        "Block ${block.number}",
                                        style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        "Transactions: ${block.transactions.size} | Gas used: ${block.gasUsed.toHumanNumber()}",
                                        color = Color.Gray
                                    )
                                    Divider(Modifier.padding(vertical = 8.dp))

                                }*/
                                Block(
                                    block.number,
                                    block.transactions.size,
                                    block.gasUsed.toHumanNumber()
                                )

                            }
                            scope.launch {
                                delay(100)
                                listState.animateScrollToItem(0, scrollOffset = 1000)
                            }
                        }
                        //Block(15949919,189,23)


                    }
                }
            }
        }
    }
}

/*@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    MainStatsScreen()
}*/
