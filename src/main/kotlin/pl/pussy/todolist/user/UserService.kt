package pl.pussy.todolist.user

interface UserService {
    fun addUser(user: AppUser): AppUser
    fun deleteUser(email: String)
}