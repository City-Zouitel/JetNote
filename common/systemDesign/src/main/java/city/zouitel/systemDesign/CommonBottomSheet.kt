package city.zouitel.systemDesign

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

data class CommonBottomSheet(val content: @Composable () -> Unit): Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 155.dp, end = 155.dp, top = 12.dp)
                    .clip(ShapeDefaults.Small)
                    .alpha(.5f),
                thickness = 5.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            content.invoke()
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}