package com.example.innertube.models.bodies

import com.example.innertube.models.Context
import com.example.innertube.models.enums.SearchType
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context = Context.DefaultWeb,
    val query: String,
    val params: String,
    val type : String = SearchType.SONG.value
)
