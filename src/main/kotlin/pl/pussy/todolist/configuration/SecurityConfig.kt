package pl.pussy.todolist.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import pl.pussy.todolist.configuration.security.CustomAuthenticationFilter
import pl.pussy.todolist.configuration.security.CustomAuthorizationFilter
import pl.pussy.todolist.user.UserServiceImpl

@Configuration
@EnableWebSecurity
class SecurityConfig(val userService: UserServiceImpl, val passwordEncoder: PasswordEncoder): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val filter = CustomAuthenticationFilter(authenticationManagerBean())
        filter.setFilterProcessesUrl("/api/login")
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(filter)
            .addFilterAfter(CustomAuthorizationFilter(userService), filter.javaClass)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/users").permitAll()
            .anyRequest().authenticated()

    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder)
    }

    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}