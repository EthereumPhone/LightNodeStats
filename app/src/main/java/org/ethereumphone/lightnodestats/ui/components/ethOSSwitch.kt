package org.ethereumphone.lightnodestats.ui.components

import android.graphics.Typeface
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.ethereumphone.lightnodestats.R

@Composable
fun ethOSSwitch(
    scale: Float = 2f,
    width: Dp = 42.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    checkedTrackColor: Color = Color(0xFF94DE7E),
    uncheckedTrackColor: Color = Color(0xFFC8C8C8),
    gapBetweenThumbAndTrackEdge: Dp = 2.dp,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,

    ) {
    val Inter = FontFamily(
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    val switchON = remember {
        mutableStateOf(checked) // Initially the switch is ON
    }

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    val paint = Paint().asFrameworkPaint().apply {
        // paint configuration
        textSize = 20f
        color = Color.White.toArgb()
        //colorFilter = ColorFilter.tint(Color.White)
        //typeface = Typeface.create(CoroutineStart.DEFAULT, Typeface.BOLD)
        typeface = Typeface.DEFAULT_BOLD
    }
    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas
                        switchON.value = !switchON.value
                        if (onCheckedChange != null) {
                            onCheckedChange(switchON.value)
                        }

                        //Turn on Lightclient
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
    ) {
        //Text(text = if (switchON.value) "ON" else "OFF")
        // Track
        drawRoundRect(

            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
            //style = Stroke(width = strokeWidth.toPx())
        )


        drawIntoCanvas {
            it.nativeCanvas.drawText(if (switchON.value) "ON" else "OFF",  if (switchON.value) 20f else 60f, 34f, paint)
        }

        // Thumb
        drawCircle(
            color = Color.White,//if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

    //Spacer(modifier = Modifier.height(18.dp))
    //Text(text = if (switchON.value) "ON" else "OFF")


}