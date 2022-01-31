package pl.pussy.todolist


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pl.pussy.todolist.todolist.Item
import pl.pussy.todolist.todolist.TodoList
import pl.pussy.todolist.todolist.TodoListService

class TodoListServiceTest {

    val repository = FakeTodoListRepository()
    val service = TodoListService(repository)

    private val USERNAME = "username"
    private val NOT_DONE_ITEM = Item(name = "itemName", done = false)
    private val DONE_ITEM = Item(name = "itemName", done = true)


    @Test
    fun `should change state of item to done`() {
        //given
            repository.save(TodoList(username = USERNAME, items = listOf(NOT_DONE_ITEM)))

        //when
            service.saveItem(USERNAME, DONE_ITEM)

        //then
            Assertions.assertEquals(listOf(DONE_ITEM), service.getList(USERNAME).items)
    }

    @Test
    fun `should save item for user todo list`() {
        //given
        service.saveItem(USERNAME, DONE_ITEM)

        //when
            val list = service.getList(USERNAME)
        //then
        Assertions.assertEquals(listOf(DONE_ITEM), list.items)
    }
}