package com.example.innertube.models.enums

import com.example.innertube.models.MusicShelfRenderer

enum class SearchType(val value: String) {
    SONG("SONG"),
    ALBUM("ALBUM"),
    ARTIST("ARTIST"),
    VIDEO("VIDEO"),
    PLAYLIST("PLAYLIST")
}