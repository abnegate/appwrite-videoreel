package io.appwrite.videoreel.core

data class Message(
    val type: Messages,
    val extras: List<Any>? = null,
)