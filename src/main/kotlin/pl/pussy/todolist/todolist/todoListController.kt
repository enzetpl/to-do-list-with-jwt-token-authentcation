package pl.pussy.todolist.todolist

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ItemController(private val todoListService: TodoListService) {

    @GetMapping("list")
    fun getList(): ListDto {
        val user = getUser()
        return todoListService.getList(user).toDto()
    }

    @DeleteMapping("/items/{name}")
    fun save(
        @PathVariable name: String
    ) {
        val user = getUser()
        todoListService.deleteItemByName(user, name)
    }


    @PostMapping("/items")
    fun save(
        @RequestBody item: Item
    ) {
        val user = getUser()
        todoListService.saveItem(user, item)
    }

    private fun getUser(): String = SecurityContextHolder.getContext().authentication.name
}

private fun TodoList.toDto() = ListDto(this.items)

data class ListDto(val items: List<Item>)
