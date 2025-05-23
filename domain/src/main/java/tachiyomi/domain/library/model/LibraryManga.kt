package tachiyomi.domain.library.model

import tachiyomi.domain.manga.model.Manga

data class LibraryManga(
    val manga: Manga,
    val category: Long,
    val totalChapters: Long,
    val readCount: Long,
    val bookmarkCount: Long,
    val fillermarkCount: Long,
    val latestUpload: Long,
    val chapterFetchedAt: Long,
    val lastRead: Long,
) {
    val id: Long = manga.id

    val unreadCount
        get() = totalChapters - readCount

    val hasBookmarks
        get() = bookmarkCount > 0

    val hasFillermarks
        get() = fillermarkCount > 0

    val hasStarted = readCount > 0
}
