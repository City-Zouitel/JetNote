package com.example.jetnote.ui.settings_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTemplateItem(
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    action: @Composable () -> Unit
) {
    Surface(onClick = {

    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon.invoke()
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    title.invoke()
                    description.invoke()
                }
            }
            action.invoke()
        }
    }
}