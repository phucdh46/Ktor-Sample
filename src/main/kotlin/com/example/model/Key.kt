package com.example.innertube.com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Key(
    val host: String = "music.youtube.com",
    val headerName: String = "X-Goog-Api-Key",
    val headerKey: String = "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8",
    val headerMask: String = "X-Goog-FieldMask",
    val hostPlayer: String = "/youtubei/v1/player",
    val hostBrowse: String = "/youtubei/v1/browse",
    val hostNext: String = "/youtubei/v1/next",
    val hostQueue: String = "/youtubei/v1/music/get_queue",
    val hostSearch: String = "/youtubei/v1/search",
    val hostSuggestion: String = "/youtubei/v1/music/get_search_suggestions",
    val visitorData : String = "CgtEUlRINDFjdm1YayjX1pSaBg%3D%3D",
    val userAgentAndroid : String = "com.google.android.apps.youtube.music/5.28.1 (Linux; U; Android 11) gzip",
    val FilterSong: String = "EgWKAQIIAWoKEAkQBRAKEAMQBA%3D%3D",
    val FilterVideo: String = "EgWKAQIQAWoKEAkQChAFEAMQBA%3D%3D",
    val FilterAlbum: String = "EgWKAQIYAWoKEAkQChAFEAMQBA%3D%3D",
    val FilterArtist: String = "EgWKAQIgAWoKEAkQChAFEAMQBA%3D%3D",
    val FilterCommunityPlaylist: String = "EgeKAQQoAEABagoQAxAEEAoQCRAF",
    val FeaturedPlaylist: String = "EgeKAQQoADgBagwQDhAKEAMQBRAJEAQ%3D",
    val embedUrl : String = "https://www.youtube.com/watch?v=",
)
