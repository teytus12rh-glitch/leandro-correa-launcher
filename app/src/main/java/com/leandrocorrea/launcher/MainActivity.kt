
package com.leandrocorrea.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Background = Color(0xFF050B18)
private val Panel = Color(0xFF101D32)
private val Neon = Color(0xFF26CFFF)
private val WhiteText = Color(0xFFF3F8FF)
private val Muted = Color(0xFF94A8C2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.rgb(5, 11, 24)
        window.navigationBarColor = android.graphics.Color.rgb(5, 11, 24)

        setContent {
            MaterialTheme {
                LauncherScreen()
            }
        }
    }
}

@Composable
private fun LauncherScreen() {
    val clock = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            clock.intValue++
            delay(1000)
        }
    }

    val now = Date()
    val time = remember(clock.intValue) {
        SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(now)
    }
    val date = remember(clock.intValue) {
        SimpleDateFormat(
            "EEEE, dd 'de' MMMM 'de' yyyy",
            Locale("pt", "BR")
        ).format(now).replaceFirstChar { it.uppercase() }
    }

    val menus = listOf(
        "⌂" to "Início",
        "▶" to "Filmes",
        "▤" to "Séries",
        "▦" to "Aplicativos",
        "⚙" to "Configurações"
    )

    val apps = listOf(
        "FLIXCINE" to "▶",
        "YouTube" to "▶",
        "TCL CHANNEL" to "▣",
        "Spotify" to "♫",
        "YouTube Music" to "♫",
        "+ Favorito" to "+"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0A1830),
                        Background,
                        Color(0xFF030711)
                    )
                )
            )
            .padding(26.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "◈ LEANDRO CORRÊA",
                        color = Neon,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "L A U N C H E R",
                        color = Muted,
                        fontSize = 12.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        time,
                        color = WhiteText,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(date, color = Muted, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(22.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(
                    modifier = Modifier.width(145.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    menus.forEachIndexed { index, item ->
                        FocusCard(
                            title = "${item.first}  ${item.second}",
                            selected = index == 0,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        InfoCard(
                            title = "CLIMA",
                            description = "Jacarezinho - PR",
                            modifier = Modifier.weight(1f)
                        )
                        InfoCard(
                            title = "TEMPERATURA",
                            description = "-- °C",
                            modifier = Modifier.weight(1f)
                        )
                        InfoCard(
                            title = "SISTEMA",
                            description = "Android TV",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Text(
                        "SEUS APLICATIVOS",
                        color = Neon,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        apps.take(3).forEach { app ->
                            AppCard(
                                title = app.first,
                                symbol = app.second,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        apps.drop(3).forEach { app ->
                            AppCard(
                                title = app.first,
                                symbol = app.second,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Text(
                        "ENTRADAS",
                        color = Neon,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf("HDMI 1", "HDMI 2 (eARC)", "HDMI 3").forEach {
                            InfoCard(
                                title = it,
                                description = "Entrada externa",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    Text(
                        "CRIADO POR LEANDRO CORRÊA  •  PRESSIONE OK PARA NAVEGAR",
                        color = Muted,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FocusCard(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val focused = remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                if (focused.intValue == 1 || selected) Panel
                else Color(0x660A1425),
                RoundedCornerShape(12.dp)
            )
            .border(
                width = if (focused.intValue == 1 || selected) 1.5.dp else 0.5.dp,
                color = if (focused.intValue == 1 || selected) Neon
                else Color(0xFF263952),
                shape = RoundedCornerShape(12.dp)
            )
            .onFocusChanged {
                focused.intValue = if (it.isFocused) 1 else 0
            }
            .focusable()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            title,
            color = if (focused.intValue == 1 || selected) Neon else WhiteText,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun AppCard(
    title: String,
    symbol: String,
    modifier: Modifier = Modifier
) {
    val focused = remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .height(112.dp)
            .background(Panel, RoundedCornerShape(14.dp))
            .border(
                width = if (focused.intValue == 1) 2.dp else 1.dp,
                color = if (focused.intValue == 1) Neon
                else Color(0xFF263952),
                shape = RoundedCornerShape(14.dp)
            )
            .onFocusChanged {
                focused.intValue = if (it.isFocused) 1 else 0
            }
            .focusable()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            symbol,
            color = Neon,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            title,
            color = WhiteText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Panel, RoundedCornerShape(12.dp))
            .border(
                0.5.dp,
                Color(0xFF263952),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, color = Neon, fontSize = 11.sp)
        Spacer(Modifier.height(5.dp))
        Text(
            description,
            color = WhiteText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
