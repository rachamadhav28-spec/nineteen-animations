package com.bro.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun AnimationDetailScreen(animationType: AnimationType, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                // Simple back arrow using a text symbol
                Text(text = "←", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = animationType.displayName, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (animationType) {
                AnimationType.FadeIn -> FadeInDemo()
                AnimationType.FadeOut -> FadeOutDemo()
                AnimationType.ScaleUp -> ScaleUpDemo()
                AnimationType.ScaleDown -> ScaleDownDemo()
                AnimationType.RotateClockwise -> RotateDemo(clockwise = true)
                AnimationType.RotateCounterClockwise -> RotateDemo(clockwise = false)
                AnimationType.SlideInFromLeft -> SlideDemo(fromLeft = true, inOut = true)
                AnimationType.SlideInFromRight -> SlideDemo(fromLeft = false, inOut = true)
                AnimationType.SlideOutToLeft -> SlideDemo(fromLeft = true, inOut = false)
                AnimationType.SlideOutToRight -> SlideDemo(fromLeft = false, inOut = false)
                AnimationType.ColorChange -> ColorChangeDemo()
                AnimationType.SizePulse -> SizePulseDemo()
                AnimationType.OpacityPulse -> OpacityPulseDemo()
                AnimationType.Bounce -> BounceDemo()
                AnimationType.FlipHorizontal -> FlipDemo(horizontal = true)
                AnimationType.FlipVertical -> FlipDemo(horizontal = false)
                AnimationType.ExpandWidth -> ExpandCollapseDemo(expand = true)
                AnimationType.CollapseWidth -> ExpandCollapseDemo(expand = false)
                AnimationType.Rotate3D -> Rotate3DDemo()
            }
        }
    }
}

@Composable
private fun FadeInDemo() {
    var visible by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = true }) { Text("Show") }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
private fun FadeOutDemo() {
    var visible by remember { mutableStateOf(true) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = false }) { Text("Hide") }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
private fun ScaleUpDemo() {
    var scaled by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(targetValue = if (scaled) 1.5f else 1f, animationSpec = tween(500))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { scaled = !scaled }) { Text("Toggle Scale") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale.value)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun ScaleDownDemo() {
    var scaled by remember { mutableStateOf(true) }
    val scale = animateFloatAsState(targetValue = if (scaled) 0.5f else 1f, animationSpec = tween(500))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { scaled = !scaled }) { Text("Toggle Scale") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale.value)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun RotateDemo(clockwise: Boolean) {
    var rotated by remember { mutableStateOf(false) }
    val angle = animateFloatAsState(targetValue = if (rotated) if (clockwise) 360f else -360f else 0f, animationSpec = tween(800))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { rotated = !rotated }) { Text("Rotate") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .rotate(angle.value)
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun SlideDemo(fromLeft: Boolean, inOut: Boolean) {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            scope.launch {
                val start = if (inOut) if (fromLeft) -300f else 300f else 0f
                val end = if (inOut) 0f else if (fromLeft) -300f else 300f
                offsetX.snapTo(start)
                offsetX.animateTo(end, animationSpec = tween(600, easing = EaseInOut))
            }
        }) { Text(if (inOut) "Slide In" else "Slide Out") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .offset(x = offsetX.value.dp)
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun ColorChangeDemo() {
    val color = remember { Animatable(MaterialTheme.colorScheme.primary) }
    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            scope.launch {
                color.animateTo(
                    targetValue = MaterialTheme.colorScheme.secondary,
                    animationSpec = tween(800)
                )
            }
        }) { Text("Change Color") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(color.value, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun SizePulseDemo() {
    var expanded by remember { mutableStateOf(false) }
    val size = animateDpAsState(targetValue = if (expanded) 180.dp else 80.dp, animationSpec = tween(500))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { expanded = !expanded }) { Text("Pulse") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(size.value)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun OpacityPulseDemo() {
    var visible by remember { mutableStateOf(true) }
    val alpha = animateFloatAsState(targetValue = if (visible) 1f else 0.2f, animationSpec = tween(600))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = !visible }) { Text("Toggle Opacity") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha.value)
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun BounceDemo() {
    var bounced by remember { mutableStateOf(false) }
    val offsetY = animateDpAsState(
        targetValue = if (bounced) (-30).dp else 0.dp,
        animationSpec = tween(durationMillis = 300, easing = androidx.compose.animation.core.Easing { fraction ->
            // simple bounce easing
            if (fraction < 0.5f) 4 * fraction * fraction else -4 * (fraction - 1) * (fraction - 1) + 1
        })
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { bounced = !bounced }) { Text("Bounce") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .offset(y = offsetY.value)
                .size(100.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun FlipDemo(horizontal: Boolean) {
    var flipped by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(600)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { flipped = !flipped }) { Text("Flip") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    if (horizontal) rotationY = rotation.value else rotationX = rotation.value
                    cameraDistance = 12 * density
                }
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun ExpandCollapseDemo(expand: Boolean) {
    var expanded by remember { mutableStateOf(expand) }
    val width = animateDpAsState(targetValue = if (expanded) 240.dp else 80.dp, animationSpec = tween(500))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { expanded = !expanded }) { Text(if (expanded) "Collapse" else "Expand") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .height(80.dp)
                .width(width.value)
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun Rotate3DDemo() {
    var rotated by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(1000)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { rotated = !rotated }) { Text("3D Rotate") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    rotationX = rotation.value
                    rotationY = rotation.value
                    cameraDistance = 12 * density
                }
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        )
    }
}
