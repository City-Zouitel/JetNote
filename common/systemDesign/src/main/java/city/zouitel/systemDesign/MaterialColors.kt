package city.zouitel.systemDesign

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class MaterialColors {

    companion object {
        const val SURFACE = "SURFACE"
        const val INVERSE_SURFACE = "INVERSE_SURFACE"
        const val ON_SURFACE = "ON_SURFACE"
        const val INVERSE_ON_SURFACE = "INVERSE_ON_SURFACE"
        const val SURFACE_VARIANT = "SURFACE_VARIANT"
        const val ON_SURFACE_VARIANT = "ON_SURFACE_VARIANT"
        const val SURFACE_TINT = "SURFACE_TINT"
        const val ERROR = "ERROR"
        const val ON_ERROR = "ON_ERROR"
        const val ERROR_CONTAINER = "ERROR_CONTAINER"
        const val ON_ERROR_CONTAINER = "ON_ERROR_CONTAINER"
        const val BACKGROUND = "BACKGROUND"
        const val ON_BACKGROUND = "ON_BACKGROUND"
        const val PRIMARY = "PRIMARY"
        const val ON_PRIMARY = "ON_PRIMARY"
        const val INVERSE_PRIMARY = "INVERSE_PRIMARY"
        const val PRIMARY_CONTAINER = "PRIMARY_CONTAINER"
        const val ON_PRIMARY_CONTAINER = "ON_PRIMARY_CONTAINER"
        const val SECONDARY = "SECONDARY"
        const val ON_SECONDARY = "ON_SECONDARY"
        const val SECONDARY_CONTAINER = "SECONDARY_CONTAINER"
        const val ON_SECONDARY_CONTAINER = "ON_SECONDARY_CONTAINER"
        const val TERTIARY = "TERTIARY"
        const val ON_TERTIARY = "ON_TERTIARY"
        const val TERTIARY_CONTAINER = "TERTIARY_CONTAINER"
        const val ON_TERTIARY_CONTAINER = "ON_TERTIARY_CONTAINER"
        const val OUTLINE = "OUTLINE"
        const val OUT_LINE_VARIANT = "OUT_LINE_VARIANT"
        const val SCRIM = "SCRIM"
    }
    //
    val getMaterialColor: @Composable (String) -> Color = {
        mapOf(
            SURFACE to MaterialTheme.colorScheme.surface,
            INVERSE_SURFACE to MaterialTheme.colorScheme.inverseSurface,
            ON_SURFACE to MaterialTheme.colorScheme.onSurface,
            INVERSE_ON_SURFACE to MaterialTheme.colorScheme.inverseOnSurface,
            SURFACE_VARIANT to MaterialTheme.colorScheme.surfaceVariant,
            ON_SURFACE_VARIANT to MaterialTheme.colorScheme.onSurfaceVariant,
            SURFACE_TINT to MaterialTheme.colorScheme.surfaceTint,
            ERROR to MaterialTheme.colorScheme.error,
            ON_ERROR to MaterialTheme.colorScheme.onError,
            ERROR_CONTAINER to MaterialTheme.colorScheme.errorContainer,
            ON_ERROR_CONTAINER to MaterialTheme.colorScheme.onErrorContainer,
            BACKGROUND to MaterialTheme.colorScheme.background,
            ON_BACKGROUND to MaterialTheme.colorScheme.onBackground,
            PRIMARY to MaterialTheme.colorScheme.primary,
            ON_PRIMARY to MaterialTheme.colorScheme.onPrimary,
            INVERSE_PRIMARY to MaterialTheme.colorScheme.inversePrimary,
            PRIMARY_CONTAINER to MaterialTheme.colorScheme.primaryContainer,
            ON_PRIMARY_CONTAINER to MaterialTheme.colorScheme.onPrimaryContainer,
            SECONDARY to MaterialTheme.colorScheme.secondary,
            ON_SECONDARY to MaterialTheme.colorScheme.onSecondary,
            SECONDARY_CONTAINER to MaterialTheme.colorScheme.secondaryContainer,
            ON_SECONDARY_CONTAINER to MaterialTheme.colorScheme.onSecondaryContainer,
            TERTIARY to MaterialTheme.colorScheme.tertiary,
            ON_TERTIARY to MaterialTheme.colorScheme.onTertiary,
            TERTIARY_CONTAINER to MaterialTheme.colorScheme.tertiaryContainer,
            ON_TERTIARY_CONTAINER to MaterialTheme.colorScheme.onTertiaryContainer,
            OUTLINE to MaterialTheme.colorScheme.outline,
            OUT_LINE_VARIANT to MaterialTheme.colorScheme.outlineVariant,
            SCRIM to MaterialTheme.colorScheme.scrim,
        ).getValue(it)
    }
}