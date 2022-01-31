package pl.pussy.todolist.todolist

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Item(
        @Id
        val id: ObjectId = ObjectId.get(),
        val name: String,
        val done: Boolean,
        @CreatedDate
        val createdDate: LocalDateTime = LocalDateTime.now(),
        @LastModifiedDate
        val modifiedDate: LocalDateTime = LocalDateTime.now()
)