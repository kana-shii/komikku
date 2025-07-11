package eu.kanade.tachiyomi.data.track.mangaupdates.dto

import eu.kanade.tachiyomi.data.track.model.TrackSearch
import eu.kanade.tachiyomi.util.lang.htmlDecode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MURecord(
    @SerialName("series_id")
    val seriesId: Long? = null,
    val title: String? = null,
    val url: String? = null,
    val description: String? = null,
    val image: MUImage? = null,
    val type: String? = null,
    val year: String? = null,
    @SerialName("bayesian_rating")
    val bayesianRating: Double? = null,
    @SerialName("rating_votes")
    val ratingVotes: Int? = null,
    @SerialName("latest_chapter")
    val latestChapter: Int? = null,
    val authors: List<MUAuthor>? = null,
)

fun MURecord.toTrackSearch(id: Long): TrackSearch {
    return TrackSearch.create(id).apply {
        remote_id = this@toTrackSearch.seriesId ?: 0L
        title = this@toTrackSearch.title?.htmlDecode() ?: ""
        total_chapters = 0
        cover_url = this@toTrackSearch.image?.url?.original ?: ""
        summary = this@toTrackSearch.description?.htmlDecode() ?: ""
        tracking_url = this@toTrackSearch.url ?: ""
        publishing_status = ""
        publishing_type = this@toTrackSearch.type.toString()
        start_date = this@toTrackSearch.year.toString()

        val sourceAuthorsList: List<MUAuthor> = this@toTrackSearch.authors ?: emptyList()

        this.authors = sourceAuthorsList
            .filter { author ->
                author.type.equals("Author(s)", ignoreCase = true) && author.name != null
            }
            .map { author -> author.name!!.trim() }
            .filter { name -> name.isNotBlank() }
            .distinct()
            .ifEmpty { emptyList() }

        this.artists = sourceAuthorsList
            .filter { author ->
                author.type.equals("Artist(s)", ignoreCase = true) && author.name != null
            }
            .map { author -> author.name!!.trim() }
            .filter { name -> name.isNotBlank() }
            .distinct()
            .ifEmpty { emptyList() }
    }
}

@Serializable
data class MUAuthor(
    val type: String? = null,
    val name: String? = null,
)
