package pl.pussy.todolist.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
data class AppUser(
    @Id
    @Indexed(unique = true)
    val email: String,
    private val password: String,
    private val authorities: List<GrantedAuthority> = emptyList()
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> {
        return this.authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}