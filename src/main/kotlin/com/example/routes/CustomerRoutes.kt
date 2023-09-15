package com.example.innertube.com.example.routes

import com.example.innertube.Innertube
import com.example.innertube.com.example.model.*
import com.example.innertube.models.bodies.*
import com.example.innertube.requests.*
import com.example.innertube.utils.completed
import com.example.innertube.utils.from
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*

fun Route.customerRouting() {
    route("/") {
        get {
            call.respondText("DHP", status = HttpStatusCode.OK)
        }
        get("{id?}") {

        }
        post {

        }
        delete("{id?}") {

        }
    }

    //SearchSuggestions
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

    //searchPage
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

    //searchPageContinuation
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

    //Related Page
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

    //QueueSong
    route("/queueSong") {
        get("{videoId?}") {
            val videoId = call.parameters["videoId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val songQueue = Innertube.song(videoId)?.getOrNull()
                println("searchPage $songQueue.")
                songQueue?.let {
                    ResponseQueue("ok",  result = it)
                } ?: ResponseQueue("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //playlistPage

    route("/playlistPage") {
        get("{browseId?}") {
            val browseId = call.parameters["browseId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val songQueue = Innertube.playlistPage(BrowseBody(browseId = browseId))?.completed()?.getOrNull()
                println("searchPage $songQueue.")
                songQueue?.let {
                    ResponsePlayListPage("ok",  result = it)
                } ?: ResponsePlayListPage("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //player
    route("/player") {
        get("{mediaId?}") {
            val mediaId = call.parameters["mediaId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val songQueue = Innertube.player(PlayerBody(videoId = mediaId))
                println("searchPage $songQueue.")
                songQueue?.onSuccess {
//                    ResponsePlayer("ok",  result = it)
                    call.respond(status = HttpStatusCode.OK, ResponsePlayer("ok",  result = it))
                }
//                ResponsePlayer("null")
                call.respond(status = HttpStatusCode.OK, ResponsePlayer("null"))
            }
//            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //nextPage
    route("/nextPage") {
//        get("{mediaId?}") {
//            val mediaId = call.parameters["mediaId"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                // Do some asynchronous work here
//                val nextPage = Innertube.nextPage(
//                    NextBody(
//                        videoId = videoId,
//                        playlistId = playlistId,
//                        params = parameters,
//                        playlistSetVideoId = playlistSetVideoId
//                    )
//                )
//                println("nextPage $nextPage.")
//                nextPage?.onSuccess {
//                    ResponsePlayer("ok",  result = it)
//                } ?: ResponsePlayer("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }
    }

    //lyric KNp1yY9s5dc
    route("/lyric") {
        get("{mediaId?}") {
            val mediaId = call.parameters["mediaId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val songQueue = Innertube.lyrics(NextBody(videoId = mediaId))
                println("searchPage $songQueue.")
                songQueue?.onSuccess {
//                    ResponseLyric("ok",  result = it)
                    call.respond(status = HttpStatusCode.OK, ResponseLyric("ok",  result = it))
                }
//                ResponseLyric("null")
                call.respond(status = HttpStatusCode.OK, ResponseLyric("null"))
            }
//            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //itemsPage
    route("/itemsPage") {
//        get("{mediaId?}") {
//            val mediaId = call.parameters["mediaId"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                // Do some asynchronous work here
//                val songQueue = Innertube.itemsPage(
//                    body = BrowseBody(
//                        browseId = endpoint.browseId!!,
//                        params = endpoint.params,
//                    ),
//                    fromMusicResponsiveListItemRenderer = Innertube.SongItem::from,
//                )
//                println("searchPage $songQueue.")
//                songQueue?.onSuccess {
//                    ResponseLyric("ok",  result = it)
//                } ?: ResponseLyric("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }
    }

    //artistPage
    route("/artistPage") {
        get("{browseId?}") {
            val browseId = call.parameters["browseId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val artistPage = Innertube.artistPage(BrowseBody(browseId = browseId))
                println("searchPage $artistPage.")
                artistPage?.onSuccess {
//                    ResponseArtistPage("ok",  result = it)
                    call.respond(status = HttpStatusCode.OK, ResponseArtistPage("ok",  result = it))
                }
//                ResponseArtistPage("null")
                call.respond(status = HttpStatusCode.OK, ResponseArtistPage("null"))
            }
//            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //albumPage MPREb_vMYegGvxkAJ
    route("/albumPage") {
        get("{browseId?}") {
            val browseId = call.parameters["browseId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val albumPage = Innertube.albumPage(BrowseBody(browseId = browseId))
                println("searchPage $albumPage.")
                albumPage?.onSuccess {
//                    ResponseAlbumPage("ok",  result = it)
                    call.respond(status = HttpStatusCode.OK, ResponseAlbumPage("ok",  result = it))

                }
                call.respond(status = HttpStatusCode.OK, ResponseAlbumPage("null"))

//                ResponseAlbumPage("null")
            }
        }
    }

}