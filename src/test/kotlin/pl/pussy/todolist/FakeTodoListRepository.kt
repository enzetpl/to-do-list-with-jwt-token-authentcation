package pl.pussy.todolist

import pl.pussy.todolist.todolist.TodoList

class FakeTodoListRepository: TodoListRepository {

    val todoLists = mutableMapOf<String, TodoList>()

    override fun findByUsername(username: String): TodoList? = todoLists.get(username)
    override fun save(todoList: TodoList): TodoList {
        todoLists.put(todoList.username, todoList)
        return todoList
    }
}