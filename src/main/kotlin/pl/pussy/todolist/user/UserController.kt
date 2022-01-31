package pl.pussy.todolist.user

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserServiceImpl) {

    @PostMapping
    fun addUser(@RequestBody user: AppUser): ResponseEntity<AppUserDto> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString())
        return ResponseEntity.created(uri).body(userService.addUser(user).toDto())
    }

    @GetMapping("/{email}")
    fun getUserByMail(@PathVariable email: String): AppUserDto {
        println(SecurityContextHolder.getContext().authentication)
        return userService.loadUserByUsername(email).toDto()
    }
}

private fun AppUser.toDto(): AppUserDto = AppUserDto(this.email)

data class AppUserDto(val email: String)
