package eu.kanade.presentation.browse.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import eu.kanade.presentation.library.components.CommonMangaItemDefaults
import eu.kanade.presentation.library.components.MangaComfortableGridItem
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.model.MangaCover
import tachiyomi.domain.manga.model.asMangaCover
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.material.padding
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun GlobalSearchCardRow(
    titles: List<Manga>,
    getManga: @Composable (Manga) -> State<Manga>,
    onClick: (Manga) -> Unit,
    onLongClick: (Manga) -> Unit,
    // KMK -->
    selection: List<Manga>,
    // KMK <--
) {
    if (titles.isEmpty()) {
        EmptyResultItem()
        return
    }

    LazyRow(
        contentPadding = PaddingValues(MaterialTheme.padding.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
    ) {
        items(titles) {
            val title by getManga(it)
            MangaItem(
                title = title.title,
                cover = title.asMangaCover(),
                isFavorite = title.favorite,
                onClick = { onClick(title) },
                onLongClick = { onLongClick(title) },
                // KMK -->
                isSelected = selection.fastAny { selected -> selected.id == title.id },
                // KMK <--
            )
        }
    }
}

@Composable
internal fun MangaItem(
    title: String,
    cover: MangaCover,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    // KMK -->
    isSelected: Boolean = false,
    // KMK <--
) {
    Box(modifier = Modifier.width(96.dp)) {
        MangaComfortableGridItem(
            title = title,
            titleMaxLines = 3,
            coverData = cover,
            coverBadgeStart = {
                InLibraryBadge(enabled = isFavorite)
            },
            coverAlpha = when {
                // KMK -->
                isSelected -> CommonMangaItemDefaults.BrowseSelectedCoverAlpha
                // KMK <--
                isFavorite -> CommonMangaItemDefaults.BrowseFavoriteCoverAlpha
                else -> 1f
            },
            onClick = onClick,
            onLongClick = onLongClick,
        )
    }
}

@Composable
internal fun EmptyResultItem() {
    Text(
        text = stringResource(MR.strings.no_results_found),
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.padding.medium,
                vertical = MaterialTheme.padding.small,
            ),
    )
}
