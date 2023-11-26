import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Switch(
    //checked: Boolean,
    //onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    switchON: MutableState<Boolean>,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
    onCheckedChange: (Boolean) -> Unit
) {

    var horizontalBias = if (switchON.value) 1f else -1f
    val alignment by animateHorizontalAlignmentAsState(horizontalBias)
    BoxWithConstraints(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (horizontalBias == -1f) Color(0xFF9FA2A5) else Color(0xFF94DE7E))
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .clickable {
                horizontalBias *= -1f
                switchON.value = horizontalBias == 1f
                onCheckedChange(switchON.value)
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
            Text(text = "".uppercase(), modifier = Modifier.padding(start=8.dp), color = if (horizontalBias == 1f) Color.White else Color.Transparent, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "".uppercase(), modifier = Modifier.padding(end=8.dp), color = if (horizontalBias == -1f) Color.White else Color.Transparent, fontWeight = FontWeight.SemiBold)
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
    val isOnlineVar = remember { mutableStateOf(false) }

    Switch(switchON = isOnlineVar) {}
}


/*@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun PreviewMainScreen() {
    ethOSTheme() {
        Switch()
    }

}*/