package com.example.gametracker

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val username: String

)