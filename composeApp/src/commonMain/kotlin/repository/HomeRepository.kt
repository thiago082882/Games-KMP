package repository

import data.models.Games
import database.datasource.GamesLocalDataSource
import database.datasource.GamesRemoteDataSource
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private val gamesLocalDataSource: GamesLocalDataSource,
    private val gamesRemoteDataSource: GamesRemoteDataSource
) {

    private suspend fun getAllGames(forceReload: Boolean = false): List<Games> {
        val cachedItems = gamesLocalDataSource.getAllGames()
        return if(cachedItems.isNotEmpty() && !forceReload) {
            println("fromCache")
            cachedItems
        } else {
            println("fromNetwork")
           gamesRemoteDataSource.getAllGames().also {
                gamesLocalDataSource.clearDb()
               gamesLocalDataSource.saveGames(it)
            }
        }
    }

    fun getGames(forceReload: Boolean = false) = flow {
        emit(getAllGames(forceReload))
    }
}