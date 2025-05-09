package eu.kanade.tachiyomi.data.backup.restore

import dev.icerock.moko.resources.StringResource
import kotlinx.collections.immutable.persistentListOf
import tachiyomi.i18n.MR
import tachiyomi.i18n.kmk.KMR

data class RestoreOptions(
    val libraryEntries: Boolean = true,
    val categories: Boolean = true,
    val hiddenDuplicates: Boolean = true,
    val appSettings: Boolean = true,
    val extensionRepoSettings: Boolean = true,
    val sourceSettings: Boolean = true,
    // SY -->
    val savedSearchesFeeds: Boolean = true,
    // SY <--
) {

    fun asBooleanArray() = booleanArrayOf(
        libraryEntries,
        categories,
        hiddenDuplicates,
        appSettings,
        extensionRepoSettings,
        sourceSettings,
        // SY -->
        savedSearchesFeeds,
        // SY <--
    )

    fun canRestore() =
        libraryEntries ||
            categories ||
            appSettings ||
            extensionRepoSettings ||
            sourceSettings /* SY --> */ ||
            savedSearchesFeeds /* SY <-- */

    companion object {
        val options = persistentListOf(
            Entry(
                label = MR.strings.label_library,
                getter = RestoreOptions::libraryEntries,
                setter = { options, enabled -> options.copy(libraryEntries = enabled) },
            ),
            Entry(
                label = MR.strings.categories,
                getter = RestoreOptions::categories,
                setter = { options, enabled -> options.copy(categories = enabled) },
            ),
            Entry(
                label = MR.strings.hidden_duplicates,
                getter = RestoreOptions::hiddenDuplicates,
                setter = { options, enabled -> options.copy(hiddenDuplicates = enabled) },
            ),
            Entry(
                label = MR.strings.app_settings,
                getter = RestoreOptions::appSettings,
                setter = { options, enabled -> options.copy(appSettings = enabled) },
            ),
            Entry(
                label = MR.strings.extensionRepo_settings,
                getter = RestoreOptions::extensionRepoSettings,
                setter = { options, enabled -> options.copy(extensionRepoSettings = enabled) },
            ),
            Entry(
                label = MR.strings.source_settings,
                getter = RestoreOptions::sourceSettings,
                setter = { options, enabled -> options.copy(sourceSettings = enabled) },
            ),
            // SY -->
            Entry(
                // KMK-->
                label = KMR.strings.saved_searches_feeds,
                // KMK <--
                getter = RestoreOptions::savedSearchesFeeds,
                setter = { options, enabled -> options.copy(savedSearchesFeeds = enabled) },
            ),
            // SY <--
        )

        fun fromBooleanArray(array: BooleanArray) = RestoreOptions(
            libraryEntries = array[0],
            categories = array[1],
            hiddenDuplicates = array[2],
            appSettings = array[3],
            extensionRepoSettings = array[4],
            sourceSettings = array[5],
            // SY -->
            savedSearchesFeeds = array[6],
            // SY <--
        )
    }

    data class Entry(
        val label: StringResource,
        val getter: (RestoreOptions) -> Boolean,
        val setter: (RestoreOptions, Boolean) -> RestoreOptions,
    )
}
