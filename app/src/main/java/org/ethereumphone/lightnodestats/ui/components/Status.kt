package org.ethereumphone.lightnodestats.ui.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joaquimverges.helium.core.event.EventDispatcher
import okio.`-DeprecatedUtf8`.size
import org.ethereumphone.lightnodestats.logic.StatsLogic
import org.ethereumphone.lightnodestats.ui.theme.*


@SuppressLint("WrongConstant")
@Composable
fun Status(
    context: Context,
    state:  StatsLogic.State,
    events:  EventDispatcher<StatsLogic.Event>
) {
    Surface(
        elevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier

                .background(Color(0xFF2C2C2C))
                .padding(12.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.isOnline) {
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.h4,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = dsuccess
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        dsuccess
                                    )
                            ) {}
                        } else {
                            Text(
                                text = "Unactive",
                                style = MaterialTheme.typography.h4,
                                fontWeight = FontWeight.Bold,
                                color = derror
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        derror
                                    )
                            ) {}
                        }


                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.button,
                        color = gray

                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AccentDark,
                            uncheckedThumbColor = gray,
                            checkedTrackColor = AccentDark,
                            uncheckedTrackColor = gray,
                        ),
                        checked = state.isOnline,
                        onCheckedChange = {
                            val cls = Class.forName("android.os.GethProxy")
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
                            events.pushEvent(StatsLogic.Event.IsOnline(it))

                        }
                    )
                }
            }

        }
    }

}

/*@Preview(showBackground = true)
@Composable
fun PreviewStatus(
) {
    ethOSTheme() {
        Status(connected = true)
    }

}*/

