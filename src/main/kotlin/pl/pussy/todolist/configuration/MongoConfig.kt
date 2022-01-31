package pl.pussy.todolist.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoConfig: AbstractMongoClientConfiguration(){
    override fun getDatabaseName(): String = "test"

    override fun mongoClient(): MongoClient {
        val connectionString: ConnectionString = ConnectionString("mongodb://localhost:27017/todolist")
        val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        return MongoClients.create(settings)
    }
}