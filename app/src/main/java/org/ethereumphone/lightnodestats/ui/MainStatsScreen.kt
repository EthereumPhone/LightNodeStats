package org.ethereumphone.lightnodestats.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.ethereumphone.lightnodestats.ui.theme.ethOSTheme

@Composable
fun MainStatsScreen(){//context: Context) {
    //val context = context
    /*val logic = LocalContext.current.getRetainedLogicBlock<StatsLogic>()
    AppBlock(logic) { state, events ->
        state?.let {*/
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    "ethOS Node",
                    style = MaterialTheme.typography.h2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Your personal gateway to Ethereum",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray
                )
                Spacer(Modifier.height(24.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Status", style = MaterialTheme.typography.h5)
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )
                    if (true){//state.isOnline) {
                        Text("✅", style = MaterialTheme.typography.h5)
                    } else {
                        Text("❌", style = MaterialTheme.typography.h5)
                    }
                }
                /*
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Connections", style = MaterialTheme.typography.h5)
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )
                    Text("${state.peerCount} peers", style = MaterialTheme.typography.h5)
                }
                 */
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Gas Price", style = MaterialTheme.typography.h5)
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )
                    //Text("${state.gasPrice} gwei", style = MaterialTheme.typography.h5)
                }
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Run Node", style = MaterialTheme.typography.h5)
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )
                    Switch(
                        checked = true,//state.isOnline,
                        onCheckedChange = {
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
                            events.pushEvent(StatsLogic.Event.IsOnline(it))*/
                        }
                    )
                }
                Spacer(Modifier.height(48.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Block Watcher",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    if (true){//state.isOnline) {
                        CircularProgressIndicator(
                            Modifier
                                .width(24.dp)
                                .height(24.dp),
                            strokeWidth = 2.dp,
                            color = Color.Gray
                        )
                    }
                }
                Divider(Modifier.padding(vertical = 8.dp))
                Spacer(Modifier.height(12.dp))
                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()
                /*LazyColumn(state = listState, reverseLayout = true) {
                    items(state.blocks.size) { index ->
                        val block = state.blocks[index]
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                "Block ${block.number}",
                                style = MaterialTheme.typography.h6
                            )
                            Text(
                                "Transactions: ${block.transactions.size} | Gas used: ${block.gasUsed.toHumanNumber()}",
                                color = Color.Gray
                            )
                            Divider(Modifier.padding(vertical = 8.dp))
                        }

                    }
                    scope.launch {
                        delay(100)
                        listState.animateScrollToItem(0, scrollOffset = 1000)
                    }
                }*/
            }
        /*}
    }*/
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    ethOSTheme{
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                "ethOS Node",
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Your personal gateway to Ethereum",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray
            )
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Status", style = MaterialTheme.typography.h5)
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
                if (true){//state.isOnline) {
                    Text("✅", style = MaterialTheme.typography.h5)
                } else {
                    Text("❌", style = MaterialTheme.typography.h5)
                }
            }
            /*
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Connections", style = MaterialTheme.typography.h5)
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
                Text("${state.peerCount} peers", style = MaterialTheme.typography.h5)
            }
             */
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Gas Price", style = MaterialTheme.typography.h5)
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
                //Text("${state.gasPrice} gwei", style = MaterialTheme.typography.h5)
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Run Node", style = MaterialTheme.typography.h5)
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
                Switch(
                    checked = true,//state.isOnline,
                    onCheckedChange = {
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
                        events.pushEvent(StatsLogic.Event.IsOnline(it))*/
                    }
                )
            }
            Spacer(Modifier.height(48.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Block Watcher",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                if (true){//state.isOnline) {
                    CircularProgressIndicator(
                        Modifier
                            .width(24.dp)
                            .height(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.Gray
                    )
                }
            }
            Divider(Modifier.padding(vertical = 8.dp))
            Spacer(Modifier.height(12.dp))
            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            /*LazyColumn(state = listState, reverseLayout = true) {
                items(state.blocks.size) { index ->
                    val block = state.blocks[index]
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            "Block ${block.number}",
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            "Transactions: ${block.transactions.size} | Gas used: ${block.gasUsed.toHumanNumber()}",
                            color = Color.Gray
                        )
                        Divider(Modifier.padding(vertical = 8.dp))
                    }

                }
                scope.launch {
                    delay(100)
                    listState.animateScrollToItem(0, scrollOffset = 1000)
                }
            }*/
        }
        /*}
    }*/
    }
}
