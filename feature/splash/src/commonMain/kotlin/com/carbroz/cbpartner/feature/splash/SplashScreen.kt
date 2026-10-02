package com.carbroz.cbpartner.feature.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val CarBrozTeal = Color(0xFF0DB7B5)
private val CarBrozTealDark = Color(0xFF079391)
private val CarBrozInk = Color(0xFF17202A)
private val CarBrozText = Color(0xFF59636E)
private val CarBrozSurface = Color(0xFFF8FCFC)
private val CarBrozMist = Color(0xFFE8F8F7)

@Composable
fun SplashScreen(
    store: SplashStore,
    modifier: Modifier = Modifier,
) {
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.accept(SplashIntent.LoadBootstrap)
    }

    val isSuccess = state is SplashState.Success

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, CarBrozSurface, CarBrozMist),
                ),
            ),
    ) {
        SplashBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            BrandHeader()
            Spacer(modifier = Modifier.weight(1f))

            CarWashIllustration(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isSuccess) "Welcome, Partner" else "Premium Car Care",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = CarBrozInk,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isSuccess) {
                    "Your partner experience is ready"
                } else {
                    "Professional car washing at your customer's doorstep"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = CarBrozText,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(26.dp))

            when (state) {
                SplashState.Initial,
                SplashState.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(0.72f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(100.dp)),
                        color = CarBrozTeal,
                        trackColor = CarBrozTeal.copy(alpha = 0.16f),
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = CarBrozTeal,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Preparing your partner experience...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CarBrozTealDark,
                        )
                    }
                }

                is SplashState.Success -> {
                    Surface(
                        shape = RoundedCornerShape(100.dp),
                        color = CarBrozTeal.copy(alpha = 0.10f),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp),
                            text = "✓  Ready to get started",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = CarBrozTealDark,
                        )
                    }
                }

                is SplashState.Failure -> {
                    val failure = state as SplashState.Failure
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = failure.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                    )

                    Button(
                        modifier = Modifier.padding(top = 14.dp),
                        onClick = { store.accept(SplashIntent.RetryBootstrap) },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CarBrozTeal,
                            contentColor = Color.White,
                        ),
                    ) {
                        Text("Try again")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "CARBROZ PARTNER",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = MaterialTheme.typography.labelSmall.letterSpacing * 1.4f,
                ),
                color = CarBrozTealDark.copy(alpha = 0.72f),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun BrandHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(CarBrozTeal),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.size(32.dp)) {
                val car = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.62f)
                    lineTo(size.width * 0.27f, size.height * 0.42f)
                    quadraticTo(size.width * 0.31f, size.height * 0.31f, size.width * 0.44f, size.height * 0.31f)
                    lineTo(size.width * 0.65f, size.height * 0.31f)
                    quadraticTo(size.width * 0.76f, size.height * 0.32f, size.width * 0.82f, size.height * 0.43f)
                    lineTo(size.width * 0.92f, size.height * 0.62f)
                }
                drawPath(
                    path = car,
                    color = Color.White,
                    style = Stroke(2.5f, cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
                drawCircle(Color.White, radius = size.width * 0.08f, center = Offset(size.width * 0.30f, size.height * 0.68f))
                drawCircle(Color.White, radius = size.width * 0.08f, center = Offset(size.width * 0.72f, size.height * 0.68f))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "CarBroz",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = CarBrozInk,
            )
            Text(
                text = "PARTNER",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = MaterialTheme.typography.labelSmall.letterSpacing * 1.8f,
                ),
                color = CarBrozTeal,
            )
        }
    }
}

@Composable
private fun CarWashIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2f
        val carWidth = size.width * 0.70f
        val carLeft = centerX - carWidth / 2f
        val carTop = size.height * 0.40f
        val carHeight = size.height * 0.34f

        drawCircle(
            color = CarBrozTeal.copy(alpha = 0.08f),
            radius = size.minDimension * 0.40f,
            center = Offset(centerX, size.height * 0.48f),
        )

        drawCircle(CarBrozTeal.copy(alpha = 0.12f), 11f, Offset(size.width * 0.18f, size.height * 0.27f))
        drawCircle(CarBrozTeal.copy(alpha = 0.18f), 6f, Offset(size.width * 0.78f, size.height * 0.24f))
        drawCircle(CarBrozTeal.copy(alpha = 0.13f), 8f, Offset(size.width * 0.86f, size.height * 0.42f))

        val roof = Path().apply {
            moveTo(carLeft + carWidth * 0.18f, carTop + carHeight * 0.18f)
            quadraticTo(carLeft + carWidth * 0.29f, carTop - carHeight * 0.13f, carLeft + carWidth * 0.43f, carTop - carHeight * 0.13f)
            lineTo(carLeft + carWidth * 0.62f, carTop - carHeight * 0.13f)
            quadraticTo(carLeft + carWidth * 0.75f, carTop - carHeight * 0.10f, carLeft + carWidth * 0.82f, carTop + carHeight * 0.18f)
            lineTo(carLeft + carWidth * 0.87f, carTop + carHeight * 0.31f)
            lineTo(carLeft + carWidth * 0.13f, carTop + carHeight * 0.31f)
            close()
        }
        drawPath(roof, CarBrozTeal, style = Stroke(7f, cap = StrokeCap.Round, join = StrokeJoin.Round))

        val body = Path().apply {
            moveTo(carLeft + carWidth * 0.10f, carTop + carHeight * 0.28f)
            quadraticTo(carLeft + carWidth * 0.05f, carTop + carHeight * 0.34f, carLeft + carWidth * 0.08f, carTop + carHeight * 0.67f)
            lineTo(carLeft + carWidth * 0.12f, carTop + carHeight * 0.79f)
            lineTo(carLeft + carWidth * 0.88f, carTop + carHeight * 0.79f)
            lineTo(carLeft + carWidth * 0.92f, carTop + carHeight * 0.67f)
            quadraticTo(carLeft + carWidth * 0.95f, carTop + carHeight * 0.34f, carLeft + carWidth * 0.90f, carTop + carHeight * 0.28f)
            close()
        }
        drawPath(body, CarBrozTeal)

        drawRoundRect(
            color = Color.White.copy(alpha = 0.94f),
            topLeft = Offset(carLeft + carWidth * 0.23f, carTop + carHeight * 0.33f),
            size = Size(carWidth * 0.54f, carHeight * 0.20f),
            cornerRadius = CornerRadius(18f, 18f),
        )
        drawRoundRect(
            color = CarBrozInk.copy(alpha = 0.10f),
            topLeft = Offset(carLeft + carWidth * 0.43f, carTop + carHeight * 0.59f),
            size = Size(carWidth * 0.14f, carHeight * 0.06f),
            cornerRadius = CornerRadius(10f, 10f),
        )

        drawCircle(CarBrozInk, carHeight * 0.17f, Offset(carLeft + carWidth * 0.24f, carTop + carHeight * 0.77f))
        drawCircle(Color.White, carHeight * 0.08f, Offset(carLeft + carWidth * 0.24f, carTop + carHeight * 0.77f))
        drawCircle(CarBrozInk, carHeight * 0.17f, Offset(carLeft + carWidth * 0.76f, carTop + carHeight * 0.77f))
        drawCircle(Color.White, carHeight * 0.08f, Offset(carLeft + carWidth * 0.76f, carTop + carHeight * 0.77f))

        listOf(
            Offset(size.width * 0.23f, size.height * 0.13f),
            Offset(size.width * 0.75f, size.height * 0.12f),
            Offset(size.width * 0.88f, size.height * 0.31f),
        ).forEachIndexed { index, point ->
            val radius = if (index == 1) 9f else 6f
            drawCircle(CarBrozTeal.copy(alpha = 0.75f), radius, point)
            drawCircle(Color.White.copy(alpha = 0.85f), radius * 0.35f, Offset(point.x - radius * 0.28f, point.y - radius * 0.28f))
        }

        drawLine(CarBrozTeal.copy(alpha = 0.55f), Offset(size.width * 0.30f, size.height * 0.17f), Offset(size.width * 0.27f, size.height * 0.24f), 3f, StrokeCap.Round)
        drawLine(CarBrozTeal.copy(alpha = 0.55f), Offset(size.width * 0.69f, size.height * 0.17f), Offset(size.width * 0.73f, size.height * 0.23f), 3f, StrokeCap.Round)
    }
}

@Composable
private fun SplashBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = CarBrozTeal.copy(alpha = 0.055f),
            radius = size.width * 0.42f,
            center = Offset(-size.width * 0.10f, -size.width * 0.08f),
        )
        drawCircle(
            color = CarBrozTeal.copy(alpha = 0.07f),
            radius = size.width * 0.34f,
            center = Offset(size.width * 1.08f, size.height * 0.80f),
        )
    }
}
