package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agychat.app.domain.model.PermissionPromptContent
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.BubblePermission
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.Shape

@Composable
fun PermissionPromptCard(
    content: PermissionPromptContent,
    onRespond: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingS)
            .border(1.dp, AgyPrimary, Shape.CardShape),
        colors = CardDefaults.cardColors(containerColor = BubblePermission),
        shape = Shape.CardShape
    ) {
        Column(modifier = Modifier.padding(Dimens.spacingM)) {
            Text(text = content.question, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(Dimens.spacingS))
            content.options.forEachIndexed { index, option ->
                PermissionOptionButton(
                    option = option,
                    isSelected = content.selectedIndex == index,
                    onSelect = { if (content.selectedIndex == null) onRespond(index) }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
