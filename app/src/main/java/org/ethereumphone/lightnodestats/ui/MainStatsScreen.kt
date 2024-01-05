package org.ethereumphone.lightnodestats.ui

import Switch
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Token
import androidx.compose.material.icons.rounded.Token
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

import org.ethosmobile.components.library.core.ethOSHeader
import org.ethosmobile.components.library.core.ethOSInfoDialog
import org.ethosmobile.components.library.core.ethOSSwitch
import org.ethosmobile.components.library.lightnode.ethOSBlock
import org.ethosmobile.components.library.lightnode.ethOSInfoBlock
import org.ethosmobile.components.library.lightnode.ethOSSwitchBlock
import org.ethosmobile.components.library.theme.Colors
import org.ethosmobile.components.library.theme.Fonts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import org.ethereumphone.lightnodestats.data.PreferencesHelper
import org.ethereumphone.lightnodestats.data.UserStore
import org.ethosmobile.components.library.core.ethOSOnboardingModalBottomSheet
import org.ethosmobile.components.library.models.OnboardingItem
import org.ethosmobile.components.library.models.OnboardingObject


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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


    val modalSheetState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()




//    var preferenceValue by remember { mutableStateOf("") }
//    // Load preference
//    LaunchedEffect(key1 = Unit) {
//        preferenceValue = PreferencesHelper.getPreference(context, "onboarding_key", "onboarding_uncomplete")
//    }
    val store = UserStore(context)
    val onboarding_complete = store.getUserOnboarding.collectAsState(initial = false)

    if(!onboarding_complete.value){
        ethOSOnboardingModalBottomSheet(
            onDismiss = {
                scope.launch {
                    modalSheetState.hide()
                    store.saveUserOnboarding(true)
                }.invokeOnCompletion {

                }

                //PreferencesHelper.setPreference(context, "onboarding_key", "onboarding_complete")
            },
            sheetState = modalSheetState,
            onboardingObject = OnboardingObject(
                imageVector = R.drawable.baseline_check_24,
                title = "Light Node",
                items = listOf(
                    OnboardingItem(
                        imageVector = Icons.Outlined.Hub,
                        title = "Light Client Proxy",
                        subtitle = "Connecting to Ethereum has never been more permissionless through your own private light node proxy"
                    ),
                    OnboardingItem(
                        imageVector = Icons.Outlined.Token,
                        title = "View Latest Blocks",
                        subtitle = "See the latest blocks loading icon & a Chain emoji ⛓\uFE0F once the node finds blocks. "
                    ),
                    OnboardingItem(
                        imageVector = Icons.Outlined.Info,
                        title = "Disclaimer",
                        subtitle = " As running the light node means that a system service must be running in the background when ON, your phone may experience a minimal decrease in battery life."
                    ),


                    )
            )
        )
    }
    //ethOSTheme() {
        AppBlock(logic) { state, events ->
            state?.let {
                val isOnlineVar = remember { mutableStateOf(state.isOnline) }
                var canGetBlocksVar = remember { mutableStateOf(state.canGetBlocks) }
                if (showBlockInfo.value) {
                    BlockDialog(
                        currentBlockToShow = currentBlockToShow,
                        setShowDialog = { showBlockInfo.value = false },
                        uiContext = uiContext
                    )
                }
                val showInfoDialog = remember { mutableStateOf(false) }
                if (showInfoDialog.value) {
                    ethOSInfoDialog(
                        setShowDialog = {
                            showInfoDialog.value = false
                        },
                        title = "Info",
                        text = "Connecting to Ethereum has never been more permissionless;" +
                                " from any location where you have data/wifi, while running" +
                                " transactions through your own private light node."
                    )
                }

                val showNetworkDialog = remember { mutableStateOf(false) }
                if (showNetworkDialog.value) {
                    ethOSInfoDialog(
                        setShowDialog = {
                            showNetworkDialog.value = false
                        },
                        title = "Network",
                        text = "We currently only support Ethereum mainnet"
                    )
                }
                val showClientDialog = remember { mutableStateOf(false) }
                if (showClientDialog.value) {
                    ethOSInfoDialog(
                        setShowDialog = {
                            showClientDialog.value = false
                        },
                        title = "Client",
                        text = "We currently only support Nimbus Light Client and Helios Light Client"
                    )
                }








                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Colors.BLACK)
                        .padding(
                            vertical = 24.dp,
                        )

                ) {
                    ethOSHeader(title="Light Node")

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 32.dp
                            )
                    ) {

                        Spacer(
                            modifier = Modifier
                                .height(height = 28.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "OFF/ON",
                                color = Colors.WHITE,
                                fontFamily = Fonts.INTER,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Box(modifier = Modifier.width(60.dp)) {
                               //ethOSBlock
                                ethOSSwitch(
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
                        }



                        Spacer(
                            modifier = Modifier
                                .height(height = 45.dp)
                        )

                        Column() {

                            ethOSInfoBlock(
                                text = "Ethereum Mainnet",
                                title = "Network",
                                hasTitle = true,
                                icon = {
                                    IconButton(
                                        modifier = Modifier.size(16.dp),
                                        onClick = { showNetworkDialog.value = true }//}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Information",
                                            tint = Colors.GRAY,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                            //.background(Color.Red)
                                        )
                                    }
                                }
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(height = 16.dp)
                            )

                            val currentClientText = getCurrentClient.invoke(obj) as String
                            val optionsArray = if (currentClientText == "Nimbus") {
                                arrayOf("Nimbus Client", "Helios Client")
                            } else {
                                arrayOf("Helios Client", "Nimbus Client")
                            }
                            ethOSSwitchBlock(
                                modifier = Modifier.fillMaxWidth(),
                                options = optionsArray,
                                onSelectedOption = {

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
                                    val changeClient =
                                        cls.getMethod("changeClient", String::class.java)
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

                                },
                                title = "Light Client",
                                hasTitle = true,
                                icon = {
                                    IconButton(
                                        modifier = Modifier.size(16.dp),
                                        onClick = { showClientDialog.value = true }//}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Information",
                                            tint = Colors.GRAY,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                            //.background(Color.Red)
                                        )
                                    }
                                }
                            )

                        }
                        Spacer(
                            modifier = Modifier
                                .height(height = 48.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Latest Blocks",
                                color = Colors.WHITE,
                                lineHeight = 109.sp,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Fonts.INTER
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(width = 8.dp)
                            )

                            if (state.isOnline) {
                                if (isOnlineVar.value && state.blocks.size > 0) {
                                    Text("⛓", style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Fonts.INTER
                                    ))
                                } else {
                                    CircularProgressIndicator(
                                        Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        strokeWidth = 2.dp,
                                        color = Colors.WHITE
                                    )
                                }
                            }


                        }

                        Spacer(
                            modifier = Modifier
                                .height(height = 36.dp)
                        )
                        val scope = rememberCoroutineScope()
                        val listState = rememberLazyListState()

                        Column(

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 18.dp)
                            ) {

                                Text(
                                    text = "#",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Colors.GRAY,
                                    modifier = Modifier.weight(.5f),
                                    textAlign = TextAlign.Start,
                                    fontFamily = Fonts.INTER

                                )
                                Text(
                                    text = "Tx",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Colors.GRAY,
                                    modifier = Modifier.weight(.3f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = Fonts.INTER

                                )
                                Text(
                                    text = "Gas",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Colors.GRAY,
                                    modifier = Modifier.weight(.3f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = Fonts.INTER
                                )
                                Box(
                                    modifier = Modifier.weight(.1f),
                                    contentAlignment = Alignment.CenterEnd,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Block Details",
                                        tint = Colors.TRANSPARENT
                                    )
                                }

                            }

                            if (state.isOnline){
                                if (isOnlineVar.value && state.blocks.size > 0) {
                                    LazyColumn(state = listState, reverseLayout = true) {
                                        items(state.blocks.size) { index ->
                                            val block = state.blocks[index]
                                            ethOSBlock(
                                                "" + block.number,
                                                "" + block.transactions.size,
                                                "" + block.gasUsed.toHumanNumber()
                                            ) {
                                                currentBlockToShow.value = block
                                                showBlockInfo.value = true
                                            }
                                        }
                                        scope.launch {
                                            delay(100)
                                            listState.animateScrollToItem(0, scrollOffset = 1000)
                                        }
//                                        item {
//                                            ethOSBlock(
//                                                number = "1464714",
//                                                tx = "174",
//                                                gas = "12M",
//                                                onClick = {}
//                                            )
//                                        }

                                    }
                                }else{
                                    //Opacity Animation
                                    val transition = rememberInfiniteTransition()
                                    val fadingAnimation by transition.animateFloat(
                                        initialValue = 1.0f,
                                        targetValue = 1f,
                                        animationSpec = infiniteRepeatable(
                                            animation = keyframes {
                                                durationMillis = 2000
                                                1.0f at 0 with LinearEasing
                                                0f at 1000 with LinearEasing
                                                1.0f at 2000 with LinearEasing
                                            }
                                        )
                                    )

                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            modifier = Modifier.alpha(fadingAnimation),
                                            text = "Finding Blocks...",
                                            fontFamily = Fonts.INTER,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Colors.GRAY,
                                            fontSize = 16.sp

                                        )
                                    }
                                }
                            }
                        }


                    }

                }
            }

        }
    //}
}

@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    MainStatsScreen(LocalContext.current)
}


@Preview
@Composable
fun previewSwitch() {
    val optionsArray = arrayOf("Nimbus client", "Helios client")
    SwitchBlock(
        options = optionsArray,
        onSelectedOption = {


        },
        title = "Light client",
        hasTitle = true,
        icon = {
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {  }//}
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
        }
    )
}