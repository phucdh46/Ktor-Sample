package com.example

import com.example.innertube.Innertube
import com.example.innertube.models.bodies.SearchSuggestionsBody
import com.example.innertube.requests.searchSuggestions
import com.example.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
