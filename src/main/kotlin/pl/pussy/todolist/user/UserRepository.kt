package pl.pussy.todolist.user

import org.springframework.data.mongodb.repository.MongoRepository

interface UserMongoRepository: MongoRepository<AppUser, String>, UserRepository {
    override fun findByEmail(username: String): AppUser?
    override fun save(user: AppUser): AppUser
    override fun deleteByEmail(email: String): AppUser
}

interface UserRepository {
    fun findByEmail(username: String): AppUser?
    fun save(user: AppUser): AppUser
    fun deleteByEmail(email: String): AppUser
}