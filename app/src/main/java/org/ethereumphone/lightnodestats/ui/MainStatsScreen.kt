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
import androidx.compose.ui.text.TextStyle
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
import org.ethereumphone.lightnodestats.ui.components.*
import org.ethereumphone.lightnodestats.ui.theme.*

@ExperimentalFoundationApi
@Composable
fun MainStatsScreen(){//context: Context) {
    /*val context = context
    val logic = LocalContext.current.getRetainedLogicBlock<StatsLogic>()
    logic.pushContext(context)*/

    ethOSTheme() {
        /*AppBlock(logic) { state, events ->
            state?.let {*/
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color(0xff1e2730))
                ) {
                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(
                                vertical = 64.dp,
                                horizontal = 32.dp
                            )
                    ) {
                        Text(
                            text = "Light Node",
                            color = Color.White,
                            lineHeight = 109.sp,
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold)
                        )
                        Spacer(
                            modifier = Modifier
                                .height(height = 28.dp))
                        ethOSSwitch(checked = true,//checkedState.value,
                            onCheckedChange = {
                                //checkedState.value = it
                                /*val cls = Class.forName("android.os.GethProxy")
                                val obj = context.getSystemService("geth");
                                if (it) {
                                    // Turn on light client
                                    val startGeth = cls.getMethod("startGeth")
                                    startGeth.invoke(obj)
                                } else {
                                    // Turn off light client
                                    val shutdownGeth = cls.getMethod("shutdownGeth")
                                    shutdownGeth.invoke(obj)
                                }
                                events.pushEvent(StatusLogic.Event.IsOnline(it))*/
                            })
                        Spacer(
                            modifier = Modifier
                                .height(height = 45.dp))

                        Column() {
                            InfoBlock(text = "Ethereum main network")
                            Spacer(
                                modifier = Modifier
                                    .height(height = 16.dp))
                            InfoBlock(text = "Nimbus client")
                        }
                        Spacer(
                            modifier = Modifier
                                .height(height = 48.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "Latest Blocks",
                                color = Color.White,
                                lineHeight = 109.sp,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(width = 8.dp))
                            CircularProgressIndicator(
                                    Modifier
                                        .width(20.dp)
                                        .height(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(height = 36.dp))
                        Column(){
                            Block("15949919","189")
                        }

                        /*if (state.isOnline) {
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
                        }*/
                        //Text("⛓", style = MaterialTheme.typography.h4)



                        /*val scope = rememberCoroutineScope()
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
                        }*/

                    }
                }
            /*}
        }*/
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    MainStatsScreen()
}
