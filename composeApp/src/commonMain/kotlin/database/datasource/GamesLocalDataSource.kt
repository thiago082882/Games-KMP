package database.datasource

import data.models.Games
import database.Database
class GamesLocalDataSource(
    private val database: Database
){
    suspend fun getAllGames(): List<Games> {
        println("database_getAllGames")
        return database.getAllGames()
    }

    suspend fun clearDb() {
        return database.clearDatabase()
    }


    suspend fun saveGames(items: List<Games>) {
        return database.createGames(items)
    }
}