package com.example.innertube.com.example.model

import com.example.innertube.Innertube
import com.example.innertube.models.PlayerResponse
import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>()

@Serializable
data class ResponseClass(val code: String, val result: List<String>? = null)

@Serializable
data class ResponseKey(val code: String, val result: Key? = null)

@Serializable
data class ResponseSearch(val code: String, val size: Int? = 0, val result: Innertube.ItemsPage<Innertube.Item>? = null)
@Serializable
data class ResponseRelated(val code: String, val size: Int? = 0, val result: Innertube.RelatedPage? = null)

@Serializable
data class ResponseQueue(val code: String,  val result: Innertube.SongItem? = null)
@Serializable
data class ResponsePlayListPage(val code: String,  val result: Innertube.PlaylistOrAlbumPage? = null)

@Serializable
data class ResponsePlayListPageContinuation(val code: String,  val result: Innertube.ItemsPage<Innertube.SongItem>? = null)

@Serializable
data class ResponsePlayer(val code: String,  val result: PlayerResponse? = null)

@Serializable
data class ResponseLyric(val code: String,  val result: String? = null)
@Serializable
data class ResponseArtistPage(val code: String,  val result: Innertube.ArtistPage? = null)
@Serializable
data class ResponseAlbumPage(val code: String,  val result: Innertube.PlaylistOrAlbumPage? = null)

@Serializable
data class ResponseNextPage(val code: String, val size: Int? = 0,  val result: Innertube.NextPage? = null)

@Serializable
data class ResponseItemsPage(val code: String, val size: Int? = 0,  val result: Innertube.ItemsPage<out Innertube.Item>? = null)