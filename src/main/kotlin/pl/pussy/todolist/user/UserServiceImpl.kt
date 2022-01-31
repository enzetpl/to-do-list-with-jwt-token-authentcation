package pl.pussy.todolist.user

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder): UserService, UserDetailsService {

    override fun loadUserByUsername(username: String): AppUser {
        return userRepository.findByEmail(username) ?: throw UsernameNotFoundException(username)
    }

    override fun addUser(user: AppUser): AppUser {
        val password = user.password
        return userRepository.save(user.copy(password = passwordEncoder.encode(password)))
    }

    override fun deleteUser(email: String) {
        userRepository.deleteByEmail(email)
    }
}