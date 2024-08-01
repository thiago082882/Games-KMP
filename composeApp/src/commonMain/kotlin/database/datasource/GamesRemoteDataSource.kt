package database.datasource




import data.models.Games
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GamesRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getAllGames(): List<Games> {
        val response = httpClient.get("http://10.0.0.118:8080/games")
        return response.body()
    }

}