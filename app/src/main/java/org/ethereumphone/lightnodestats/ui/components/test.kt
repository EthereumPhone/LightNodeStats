import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import org.ethereumphone.lightnodestats.ui.theme.ethOSTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestSwitch(
    //checked: Boolean,
    //onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
    onCheckedChange: (Boolean) -> Unit
) {

    var switchON by remember {
        mutableStateOf(enabled) // Initially the switch is ON
    }

    val startValue = if(enabled) 1f else -1f

    var horizontalBias by remember { mutableStateOf(startValue) }
    val alignment by animateHorizontalAlignmentAsState(horizontalBias)
    BoxWithConstraints(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (horizontalBias == -1f) Color(0xFFC8C8C8) else Color(0xFF94DE7E))
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                horizontalBias *= -1f
                switchON = horizontalBias == 1f
                onCheckedChange(switchON)

            }

    ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(alignment)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "on".uppercase(), modifier = Modifier.padding(start=8.dp), color = if (horizontalBias == 1f) Color.White else Color.Transparent, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "off".uppercase(), modifier = Modifier.padding(end=8.dp), color = if (horizontalBias == -1f) Color.White else Color.Transparent, fontWeight = FontWeight.SemiBold)
            }
    }
}

@Composable
private fun animateHorizontalAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}

@Preview
@Composable
fun TestPeview() {
    TestSwitch() {}
}


/*@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    ethOSTheme() {
        TestSwitch()
    }

}*/