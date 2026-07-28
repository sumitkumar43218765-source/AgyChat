package com.agychat.app.presentation.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.PlanArtifact
import com.agychat.app.presentation.theme.AgySurfaceElevatedDark
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.CardShape
import com.agychat.app.presentation.theme.Dimens

@Composable
fun PlanCard(artifact: PlanArtifact) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.PaddingNormal),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = AgySurfaceElevatedDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingNormal)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = artifact.title,
                style = MaterialTheme.typography.titleLarge,
                color = AgyTextPrimary,
                modifier = Modifier.padding(bottom = Dimens.PaddingSmall)
            )
            Text(
                text = artifact.content,
                style = MaterialTheme.typography.bodyMedium,
                color = AgyTextPrimary.copy(alpha = 0.8f)
            )
        }
    }
}
