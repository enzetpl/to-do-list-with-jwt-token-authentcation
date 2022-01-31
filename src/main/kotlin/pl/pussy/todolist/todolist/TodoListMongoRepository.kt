package pl.pussy.todolist.todolist

import org.springframework.data.mongodb.repository.MongoRepository

interface TodoListMongoRepository: MongoRepository<TodoList, String>, TodoListRepository {
    override fun findByUsername(username: String): TodoList?
}


interface TodoListRepository {
    fun findByUsername(username: String): TodoList?
    fun save(todoList: TodoList): TodoList
}