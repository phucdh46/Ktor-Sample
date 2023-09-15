package com.example

import com.example.innertube.Innertube
import com.example.innertube.models.bodies.SearchSuggestionsBody
import com.example.innertube.requests.searchSuggestions
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt()?:8080) {
        io.ktor.server.netty.EngineMain.main(args)
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
