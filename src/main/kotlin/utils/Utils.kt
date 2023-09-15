package com.example.innertube.utils

import io.ktor.utils.io.CancellationException
import com.example.innertube.Innertube
import com.example.innertube.models.SectionListRenderer
import com.example.innertube.models.bodies.ContinuationBody
import com.example.innertube.requests.playlistPage

internal fun SectionListRenderer.findSectionByTitle(text: String): SectionListRenderer.Content? {
    return contents?.find { content ->
        val title = content
            .musicCarouselShelfRenderer
            ?.header
            ?.musicCarouselShelfBasicHeaderRenderer
            ?.title
            ?: content
                .musicShelfRenderer
                ?.title

        title
            ?.runs
            ?.firstOrNull()
            ?.text == text
    }
}

internal fun SectionListRenderer.findSectionByStrapline(text: String): SectionListRenderer.Content? {
    return contents?.find { content ->
        content
            .musicCarouselShelfRenderer
            ?.header
            ?.musicCarouselShelfBasicHeaderRenderer
            ?.strapline
            ?.runs
            ?.firstOrNull()
            ?.text == text
    }
}

internal inline fun <R> runCatchingNonCancellable(block: () -> R): Result<R>? {
    val result = runCatching(block)
    return when (result.exceptionOrNull()) {
        is CancellationException -> null
        else -> result
    }
}

infix operator fun <T : Innertube.Item> Innertube.ItemsPage<T>?.plus(other: Innertube.ItemsPage<T>) =
    other.copy(
        items = (this?.items?.plus(other.items ?: emptyList())
            ?: other.items)?.distinctBy(Innertube.Item::key)
    )

suspend fun Result<Innertube.PlaylistOrAlbumPage>.completed(): Result<Innertube.PlaylistOrAlbumPage>? {
    var playlistPage = getOrNull() ?: return null

    while (playlistPage.songsPage?.continuation != null) {
        val continuation = playlistPage.songsPage?.continuation!!
        val otherPlaylistPageResult = Innertube.playlistPage(ContinuationBody(continuation = continuation)) ?: break

        if (otherPlaylistPageResult.isFailure) break

        otherPlaylistPageResult.getOrNull()?.let { otherSongsPage ->
            playlistPage = playlistPage.copy(songsPage = playlistPage.songsPage + otherSongsPage)
        }
    }

    return Result.success(playlistPage)
}