package pl.pussy.todolist.configuration.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.pussy.todolist.user.AppUser
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(val authentiactionManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authentiactionManager.authenticate(usernamePasswordAuthenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user: AppUser = authResult.principal as AppUser
        val algorithm: Algorithm = Algorithm.HMAC512("")
        val accessToken = AccessToken(JWT.create()
            .withSubject(user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000*60*10)).withIssuer("test")
            .withClaim("roles", user.authorities.map { it.authority })
            .sign(algorithm))
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, accessToken)
    }
}

data class AccessToken(val token: String)
