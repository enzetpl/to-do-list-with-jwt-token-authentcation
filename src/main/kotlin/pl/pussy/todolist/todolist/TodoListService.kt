package pl.pussy.todolist.todolist

import org.springframework.stereotype.Service

@Service
class TodoListService(private val todoListRepository: TodoListRepository) {

    fun saveItem(username: String, item: Item) {
        val todoList = todoListRepository.findByUsername(username) ?: create(username, item)
        val itemsToSave = todoList.items.filter { it.name != item.name } + item
        todoListRepository.save(todoList.copy(items = itemsToSave))
    }

    private fun create(username: String, item: Item) =
        todoListRepository.save(TodoList(username = username, items = listOf(item)))

    fun getList(username: String): TodoList {
        return todoListRepository.findByUsername(username) ?: TodoList(username = username, items = emptyList())
    }

    fun deleteItemByName(username: String, name: String) {
        val list = getList(username)
        val itemsToSave = list.items.filterNot { it.name == name }
        saveList(list.copy(items = itemsToSave))
    }

    private fun saveList(todoList: TodoList) = todoListRepository.save(todoList)
}