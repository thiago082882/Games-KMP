package database



import app.cash.sqldelight.async.coroutines.awaitAsList
import data.models.Games
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Database(private val dbHelper: DbHelper, private val scope: CoroutineScope) {


    fun clearDatabase() {
        scope.launch {
            dbHelper.withDatabase { database ->
                database.appDatabaseQueries.removeAllGames()
            }
        }
    }


    suspend fun getAllGames(): List<Games> {
        var items: List<Games>
        val result = scope.async {
            dbHelper.withDatabase { database ->
                items = database.appDatabaseQueries.selectAllGames(::mapGameSelecting)
                    .awaitAsList()
                items
            }
        }

        return result.await()
    }

    private fun mapGameSelecting(
        id: Long,
        title: String,
        image: String,
        description: String?,
    ): Games {

        return Games(
            id = id.toInt(),
            shortDescription = description,
            title = title,
            imgUrl = image
        )
    }

    suspend fun createGames(items: List<Games>) {
        val result = scope.async {
            dbHelper.withDatabase { database ->
                items.forEach {
                    insertGame(it)
                }
            }
        }
    }

    private suspend fun insertGame(item: Games) {
        scope.async {
            dbHelper.withDatabase { database ->
                println("insertGame_item=$item")
                database.appDatabaseQueries.insertGames(
                    id = item.id?.toLong(),
                    title = item.title.toString(),
                    image = item.imgUrl.toString(),
                    description = item.shortDescription.toString(),
                )
            }
        }
    }
}