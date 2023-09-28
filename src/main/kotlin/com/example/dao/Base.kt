package com.example.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class Base<T>(
    @JsonProperty("_key")
    val key: String,
    @JsonProperty("_id")
    val id: String,
    @JsonProperty("_rev")
    val rev: String,
    @JsonProperty("content")
    @Contextual val content: T? = null
)

@JsonIgnoreProperties(ignoreUnknown = false)
class Content<T> {
    @Contextual val data: HashMap<String, @Contextual Any>? = null
    val errors: String? = null
    val requestId: String = ""
    val status: String = ""
}
