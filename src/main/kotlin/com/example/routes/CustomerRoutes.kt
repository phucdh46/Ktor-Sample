package com.example.innertube.com.example.routes

import com.example.innertube.Innertube
import com.example.innertube.com.example.model.*
import com.example.innertube.models.bodies.*
import com.example.innertube.models.enums.SearchType
import com.example.innertube.requests.*
import com.example.innertube.utils.completed
import com.example.innertube.utils.from
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
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
        get("{query}") {
            val query = call.parameters["query"].orEmpty()

            val result = withContext(Dispatchers.Default) {
                val searchSuggestions = Innertube.searchSuggestions(SearchSuggestionsBody(input = query))
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
//        get("{query}") {
//            val query = call.parameters["query"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                val searchPage = Innertube.searchPage(
//                    body = SearchBody(query = query, params = Innertube.SearchFilter.Song.value),
//                    fromMusicShelfRendererContent = Innertube.SongItem.Companion::from
//                )
//                println("searchPage $searchPage.")
//                searchPage?.getOrNull()?.let {
//                    ResponseSearch("ok", it.items?.size, it)
//                } ?: ResponseSearch("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }

        post {
            val searchBody = call.receive<SearchBody>()
            val type = when(searchBody.type) {
                SearchType.ALBUM.value -> Innertube.AlbumItem::from
                SearchType.ARTIST.value -> Innertube.ArtistItem::from
                SearchType.VIDEO.value -> Innertube.VideoItem::from
                SearchType.PLAYLIST.value -> Innertube.PlaylistItem::from
                else -> Innertube.SongItem.Companion::from
            }
            val params = when(searchBody.type) {
                SearchType.ALBUM.value -> Innertube.SearchFilter.Album.value
                SearchType.ARTIST.value -> Innertube.SearchFilter.Artist.value
                SearchType.VIDEO.value -> Innertube.SearchFilter.Video.value
                SearchType.PLAYLIST.value -> Innertube.SearchFilter.CommunityPlaylist.value
                else -> Innertube.SearchFilter.Song.value
            }
            val result = withContext(Dispatchers.Default) {
                val searchPage = Innertube.searchPage(
                    body = SearchBody(query = searchBody.query, params = params),
                    fromMusicShelfRendererContent = type
                )
                println("searchPage $searchPage.")
                searchPage?.getOrNull()?.let {
                    ResponseSearch("ok", it.items?.size, it)
                } ?: ResponseSearch("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }

//        post("/continuation") {
//            val searchBody = call.receive<SearchBody>()
//            val type = when(searchBody.type) {
//                SearchType.ALBUM.value -> Innertube.AlbumItem::from
//                SearchType.ARTIST.value -> Innertube.ArtistItem::from
//                SearchType.VIDEO.value -> Innertube.VideoItem::from
//                SearchType.PLAYLIST.value -> Innertube.PlaylistItem::from
//                else -> Innertube.SongItem.Companion::from
//            }
//            val result = withContext(Dispatchers.Default) {
//                val searchPage = Innertube.searchPage(
//                    body = ContinuationBody(continuation = searchBody.query),
//                    fromMusicShelfRendererContent = type
//                )
//                println("searchPage $searchPage.")
//                searchPage?.getOrNull()?.let {
//                    ResponseSearch("ok", it.items?.size, it)
//                } ?: ResponseSearch("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }
    }

    //searchPageContinuation
//    route("/search/continuation") {
//        get("{query}") {
//            val query = call.parameters["query"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                // Do some asynchronous work here
//                val searchPage = Innertube.searchPage(
//                    body = ContinuationBody(continuation = "1"),
//                    fromMusicShelfRendererContent = Innertube.SongItem.Companion::from
//                )
//                println("searchPage $searchPage.")
//                searchPage?.getOrNull()?.let {
//                    ResponseSearch("ok", it.items?.size, it)
//                } ?: ResponseSearch("null")
//            }
//
//            call.respond(status = HttpStatusCode.OK, result)
//
//        }
//    }

    //Related Page M6n0XBJqLfM
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

    //playlistPage UC08qAR-kyTJb8Xjwbo6C7ZQ
    route("/playlistPage") {
//        get("{browseId?}") {
//            val browseId = call.parameters["browseId"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                // Do some asynchronous work here
//                val songQueue = Innertube.playlistPage(BrowseBody(browseId = browseId))?.completed()?.getOrNull()
//                println("searchPage $songQueue.")
//                songQueue?.let {
//                    ResponsePlayListPage("ok",  result = it)
//                } ?: ResponsePlayListPage("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }

        post {
            val browseBody = call.receive<BrowseBody>()

            val result = withContext(Dispatchers.Default) {
                val playlistPage = Innertube.playlistPage(
                    body = browseBody,
                )
                println("playlistPage $playlistPage.")
                playlistPage?.getOrNull()?.let {
                    ResponsePlayListPage("ok", it)
                } ?: ResponsePlayListPage("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }

        //continuation
//        get("/continuation/{continuation?}") {
//            val continuation = call.parameters["continuation"].orEmpty()
//            val result = withContext(Dispatchers.Default) {
//                // Do some asynchronous work here
//                val songQueue = Innertube.playlistPage(ContinuationBody(continuation = continuation))
//                println("searchPage $songQueue.")
//                songQueue?.onSuccess {
//                    ResponsePlayListPageContinuation("ok",  result = it)
//                } ?: ResponsePlayListPageContinuation("null")
//            }
//            call.respond(status = HttpStatusCode.OK, result)
//        }
    }


    //player _osQnC8dNyw
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
        post {
            val nextBody = call.receive<NextBody>()

            val result = withContext(Dispatchers.Default) {
                val searchPage = Innertube.nextPage(body = nextBody)
                println("searchPage $searchPage.")
                searchPage?.getOrNull()?.let {
                    ResponseNextPage("ok", it.itemsPage?.items?.size, it)
                } ?: ResponseNextPage("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //lyric KNp1yY9s5dc
    route("/lyric") {
        get("{mediaId?}") {
            val mediaId = call.parameters["mediaId"].orEmpty()
            withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val songQueue = Innertube.lyrics(NextBody(videoId = mediaId))
                println("searchPage $songQueue.")
                songQueue?.onSuccess {
                    call.respond(status = HttpStatusCode.OK, ResponseLyric("ok",  result = it))
                }
                call.respond(status = HttpStatusCode.OK, ResponseLyric("null"))
            }
        }
    }

    //itemsPage UC08qAR-kyTJb8Xjwbo6C7ZQ
    route("/itemsPage") {
        post {
            val browseBody = call.receive<BrowseBody>()

            val result = withContext(Dispatchers.Default) {
                val itemsPage = when(browseBody.params) {
                    SearchType.ALBUM.value -> Innertube.itemsPage(body = browseBody, fromMusicTwoRowItemRenderer = Innertube.AlbumItem::from)
                    else -> Innertube.itemsPage(browseBody, fromMusicResponsiveListItemRenderer = Innertube.SongItem::from)
                }

                println("itemsPage $itemsPage.")
                itemsPage?.getOrNull()?.let {
                    ResponseItemsPage("ok", it.items?.size, it)
                } ?: ResponseItemsPage("null")
            }
            call.respond(status = HttpStatusCode.OK, result)
        }
    }

    //artistPage UC08qAR-kyTJb8Xjwbo6C7ZQ
    route("/artistPage") {
        get("{browseId?}") {
            val browseId = call.parameters["browseId"].orEmpty()
            val result = withContext(Dispatchers.Default) {
                // Do some asynchronous work here
                val artistPage = Innertube.artistPage(BrowseBody(browseId = browseId))
                println("searchPage $artistPage.")
                artistPage?.onSuccess {
                    call.respond(status = HttpStatusCode.OK, ResponseArtistPage("ok",  result = it))
                }
                call.respond(status = HttpStatusCode.OK, ResponseArtistPage("null"))
            }
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
                    call.respond(status = HttpStatusCode.OK, ResponseAlbumPage("ok",  result = it))
                }
                call.respond(status = HttpStatusCode.OK, ResponseAlbumPage("null"))
            }
        }
    }

}