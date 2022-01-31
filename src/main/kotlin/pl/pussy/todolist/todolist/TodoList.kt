package pl.pussy.todolist.todolist

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class TodoList(
    @Id
    val id: ObjectId = ObjectId.get(),
    val username: String,
    val items: List<Item>
)