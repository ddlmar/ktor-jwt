package com.routing.response

import com.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String
) {
}