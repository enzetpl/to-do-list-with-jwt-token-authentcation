package pl.pussy.todolist.configuration.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import pl.pussy.todolist.user.UserServiceImpl
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter(val userServiceImpl: UserServiceImpl): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI == "/api/users") {
            response.status = 200
            filterChain.doFilter(request, response)
        } else {
            val bearer = request.getHeader(AUTHORIZATION).substring(7)
            val algorithm: Algorithm = Algorithm.HMAC512("")
            val verifier = JWT.require(algorithm).build()
            val token = verifier.verify(bearer)
            val roles: Collection<GrantedAuthority> = token.getClaim("roles").asList(SimpleGrantedAuthority::class.java)
            val username = token.subject
            if (token.expiresAt.before(Date(System.currentTimeMillis()))) throw IllegalAccessException("expired")
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, null, roles)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            userServiceImpl.loadUserByUsername(username)
            filterChain.doFilter(request, response)
        }
    }
}