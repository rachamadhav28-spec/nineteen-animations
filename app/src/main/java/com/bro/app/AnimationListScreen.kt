package com.bro.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimationListScreen(onAnimationSelected: (AnimationType) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(AnimationType.values().toList()) { animation ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAnimationSelected(animation) }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = animation.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
            }
            Divider()
        }
    }
}
