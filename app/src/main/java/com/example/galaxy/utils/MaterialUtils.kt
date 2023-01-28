package com.example.galaxy.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.example.galaxy.ui.theme.GalaxyTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberInsetsPaddingValues


@Composable
inline fun ConstraintContainer(
    modifier: Modifier = Modifier,
    crossinline content: @Composable ConstraintLayoutScope.() -> Unit
) {
    val paddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.navigationBars,
        applyBottom = true
    )
    val bottomPadding = paddingValues.calculateBottomPadding()
    ConstraintLayout(
        modifier = modifier
            .padding(bottom = bottomPadding)
            .navigationBarsWithImePadding(),
        content = content
    )
}

@Composable
fun InsetContent(
    consumeWindowInsets: Boolean = true,
    windowInsetsAnimationsEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    GalaxyTheme {
        ProvideWindowInsets(
            consumeWindowInsets = consumeWindowInsets,
            windowInsetsAnimationsEnabled = windowInsetsAnimationsEnabled,
            content = content
        )
    }
}