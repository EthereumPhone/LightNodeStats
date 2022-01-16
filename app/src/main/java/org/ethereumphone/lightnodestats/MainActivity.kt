package org.ethereumphone.lightnodestats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.ethereumphone.lightnodestats.logic.StatsLogic
import org.ethereumphone.lightnodestats.ui.MainStatsScreen
import org.ethereumphone.lightnodestats.ui.theme.LightNodeStatsTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LightNodeStatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainStatsScreen()
                }
            }
        }
    }
}

const val TAG = "LightNodeStatsApp"