package com.agychat.app.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.PtyConnectionState
import com.agychat.app.domain.model.StatusLineContent
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.Dimens

@Composable
fun ChatStatusHeader(
    model: StatusLineContent?,
    connectionState: PtyConnectionState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AgySurfaceDark)
            .padding(Dimens.SpacingSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = model?.text ?: "Unknown Model", color = androidx.compose.ui.graphics.Color.LightGray)
        Text(text = connectionState.name, color = androidx.compose.ui.graphics.Color.Green)
    }
}
