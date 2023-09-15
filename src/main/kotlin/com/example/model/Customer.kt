package com.example.innertube.com.example.model

import com.example.innertube.Innertube
import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>()

@Serializable
data class ResponseClass(val code: String, val result: List<String>? = null)

@Serializable
data class ResponseSearch(val code: String, val size: Int? = 0,  val result: Innertube.ItemsPage<Innertube.SongItem>? = null)
@Serializable
data class ResponseRelated(val code: String, val size: Int? = 0,  val result: Innertube.RelatedPage? = null)