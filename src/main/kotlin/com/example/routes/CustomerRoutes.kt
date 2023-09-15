package com.example.innertube.com.example.routes

import com.example.innertube.Innertube
import com.example.innertube.com.example.model.Customer
import com.example.innertube.com.example.model.ResponseClass
import com.example.innertube.com.example.model.ResponseRelated
import com.example.innertube.com.example.model.ResponseSearch
import com.example.innertube.models.bodies.ContinuationBody
import com.example.innertube.models.bodies.NextBody
import com.example.innertube.models.bodies.SearchBody
import com.example.innertube.models.bodies.SearchSuggestionsBody
import com.example.innertube.requests.relatedPage
import com.example.innertube.requests.searchPage
import com.example.innertube.requests.searchSuggestions
import com.example.innertube.utils.from
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*

val customerStorage = mutableListOf<Customer>()

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {

        }
        post {

        }
        delete("{id?}") {

        }
    }

    route("/searchSuggestions") {
        get {

            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val searchSuggestions = Innertube.searchSuggestions(SearchSuggestionsBody(input = "music"))
                println("searchSuggestions $searchSuggestions.")
                searchSuggestions?.getOrNull()?.let {
                    ResponseClass("ok", it)
                } ?: ResponseClass("null")
            }

            call.respond(status = HttpStatusCode.OK, result)

        }
    }

    route("/search") {
        get("{query}") {
            val query = call.parameters["query"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val searchPage = Innertube.searchPage(
                    body = SearchBody(query = query, params = Innertube.SearchFilter.Song.value),
                    fromMusicShelfRendererContent = Innertube.SongItem.Companion::from
                )
                println("searchPage $searchPage.")
                searchPage?.getOrNull()?.let {
                    ResponseSearch("ok", it.items?.size, it)
                } ?: ResponseSearch("null")
            }

            call.respond(status = HttpStatusCode.OK, result)

        }
    }

    route("/search/continuation") {
        get("{query}") {
            val query = call.parameters["query"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val searchPage = Innertube.searchPage(
                    body = ContinuationBody(continuation = "2"),
                    fromMusicShelfRendererContent = Innertube.SongItem.Companion::from
                )
                println("searchPage $searchPage.")
                searchPage?.getOrNull()?.let {
                    ResponseSearch("ok", it.items?.size, it)
                } ?: ResponseSearch("null")
            }

            call.respond(status = HttpStatusCode.OK, result)

        }
    }

    route("/related") {
        get("{videoId?}") {
            val id = call.parameters["videoId"]
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val searchPage = Innertube.relatedPage(NextBody(videoId = (id ?: "J7p4bzqLvCw")))
                println("searchPage $searchPage.")
                searchPage?.getOrNull()?.let {
                    val size = (it.albums?.size ?: 0) + (it.artists?.size ?: 0) + (it.playlists?.size ?: 0) + (it.songs?.size ?: 0)
                    ResponseRelated("ok", size,  result = it)
                } ?: ResponseRelated("null")
            }

            call.respond(status = HttpStatusCode.OK, result)

        }
    }

}