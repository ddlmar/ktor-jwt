package com.routing


import com.model.User
import com.routing.request.UserRequest
import com.routing.response.UserResponse
import com.service.UserService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {
    post {
        val userRequest = call.receive<UserRequest>()

        val createUser = userService.save(
            user = userRequest.toModel()
        ) ?: return@post call.respond(
            message = HttpStatusCode.BadRequest,
        )

        call.response.header(
            name = "id",
            value = createUser.id.toString()
        )

        call.respond(
            message = HttpStatusCode.Created
        )

        get {
            val users = userService.findAll()

            call.respond(
                message = users.map(User::toResponse)
            )
        }

        get("/{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val foundUser = userService.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(
                message = foundUser.toResponse()
            )
        }
    }
}

private fun UserRequest.toModel(): User = User(
    id = UUID.randomUUID(),
    username = this.username,
    password = this.password
)

private fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        username = this.username
    )