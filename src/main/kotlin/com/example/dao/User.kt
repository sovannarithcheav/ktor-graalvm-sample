package com.example.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class User {
    @JsonProperty("_key")
    val key: String = ""

    @JsonProperty("_id")
    val id: String = ""

    @JsonProperty("_rev")
    val rev: String = ""

    @JsonProperty("content")
    @Contextual val content: Content<Any?>? = null
}