package org.ethereumphone.lightnodestats.ui

import TestSwitch
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.joaquimverges.helium.compose.AppBlock
import com.joaquimverges.helium.core.retained.getRetainedLogicBlock
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ethereumphone.lightnodestats.R
import org.ethereumphone.lightnodestats.data.toHumanNumber
import org.ethereumphone.lightnodestats.logic.StatsLogic
import org.ethereumphone.lightnodestats.ui.components.*
import org.ethereumphone.lightnodestats.ui.theme.*
import org.web3j.protocol.core.methods.response.EthBlock

@ExperimentalFoundationApi
@Composable
fun MainStatsScreen(context: Context) {
    val context = context
    val logic = LocalContext.current.getRetainedLogicBlock<StatsLogic>()
    logic.pushContext(context)
    val cls = Class.forName("android.os.GethProxy")
    val obj = context.getSystemService("geth")
    val getCurrentClient = cls.getMethod("getCurrentClient")
    var showBlockInfo = remember { mutableStateOf(false) }
    var currentBlockToShow = remember { mutableStateOf<EthBlock.Block?>(null) }
    val uiContext = LocalContext.current

    val Inter = FontFamily(
        Font(R.font.inter_light,FontWeight.Light),
        Font(R.font.inter_regular,FontWeight.Normal),
        Font(R.font.inter_medium,FontWeight.Medium),
        Font(R.font.inter_semibold,FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold)
    )
    ethOSTheme() {
        AppBlock(logic) { state, events ->
            state?.let {
                val isOnlineVar = remember { mutableStateOf(state.isOnline) }
                var canGetBlocksVar = remember { mutableStateOf(state.canGetBlocks) }
                if (showBlockInfo.value) {
                    BlockDialog(
                        currentBlockToShow = currentBlockToShow,
                        setShowDialog={ showBlockInfo.value = false },
                        uiContext = uiContext
                    )
                }
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
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter)
                        )
                        Spacer(
                            modifier = Modifier
                                .height(height = 28.dp))


                            Box(modifier = Modifier.width(90.dp)){
                                TestSwitch(
                                    switchON = isOnlineVar,
                                    onCheckedChange = {
                                        if (it) {
                                            // Turn on light client
                                            val startGeth = cls.getMethod("startGeth")
                                            startGeth.invoke(obj)
                                        } else {
                                            // Turn off light client
                                            val shutdownGeth = cls.getMethod("shutdownGeth")
                                            shutdownGeth.invoke(obj)
                                        }
                                        events.pushEvent(StatsLogic.Event.IsOnline(it))
                                    }
                                )
                            }

                        Spacer(
                            modifier = Modifier
                                .height(height = 45.dp))

                        Column() {
                            InfoBlock(text = "Ethereum main network")
                            Spacer(
                                modifier = Modifier
                                    .height(height = 16.dp))
                            InfoBlock(text = "Nimbus client")
                            val currentClientText = getCurrentClient.invoke(obj) as String
                            val optionsArray = if (currentClientText == "Nimbus") {
                                arrayOf("Nimbus client", "Helios Client")
                            } else {
                                arrayOf("Helios Client", "Nimbus client")
                            }
                            SwitchBlock(
                                options = optionsArray
                            ) {
                                println("Selected $it")
                                var before = state.isOnline

                                if (before) {
                                    // Turn of client first
                                    val shutdownGeth = cls.getMethod("shutdownGeth")
                                    shutdownGeth.invoke(obj)
                                    events.pushEvent(StatsLogic.Event.IsOnline(false))
                                    isOnlineVar.value = false
                                    events.pushEvent(StatsLogic.Event.CanGetBlocks(false))
                                    state.canGetBlocks = false
                                }
                                val changeClient = cls.getMethod("changeClient", String::class.java)
                                if (it == "Nimbus client") {
                                    changeClient.invoke(obj, "Nimbus")
                                } else {
                                    changeClient.invoke(obj, "Helios")
                                }

                                if (before) {
                                    // Start client again
                                    // Delay the starting of the client
                                    Thread {
                                        Thread.sleep(1000)
                                        val startGeth = cls.getMethod("startGeth")
                                        startGeth.invoke(obj)
                                        events.pushEvent(StatsLogic.Event.IsOnline(true))
                                        isOnlineVar.value = true
                                        events.pushEvent(StatsLogic.Event.CanGetBlocks(false))
                                        state.canGetBlocks = false
                                        events.pushEvent(StatsLogic.Event.IsOnline(true))
                                        state.isOnline = true
                                        canGetBlocksVar.value = false
                                    }.start()
                                }

                                state.blocks.clear()
                            }
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
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Inter
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(width = 8.dp))

                            if (state.isOnline) {
                                if (isOnlineVar.value && state.blocks.size > 0) {
                                    Text("⛓", style = MaterialTheme.typography.h4)
                                } else {
                                    CircularProgressIndicator(
                                        Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.White
                                    )
                                }
                            }



                        }

                        Spacer(
                            modifier = Modifier
                                .height(height = 36.dp))
                        val scope = rememberCoroutineScope()
                        val listState = rememberLazyListState()
                        LazyColumn(state = listState, reverseLayout = true) {
                            items(state.blocks.size) { index ->
                                val block = state.blocks[index]
                                Block(
                                    ""+block.number,
                                    ""+block.transactions.size,
                                    ""+block.gasUsed.toHumanNumber()
                                ) {
                                    currentBlockToShow.value = block
                                    showBlockInfo.value = true
                                }
                            }
                            scope.launch {
                                delay(100)
                                listState.animateScrollToItem(0, scrollOffset = 1000)
                            }
                        }

                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    MainStatsScreen(LocalContext.current)
}
