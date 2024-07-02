package repository

import data.models.Games
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private  val httpClient: HttpClient
) {

    suspend fun getGamesApi(): List<Games> {
        val response = httpClient.get("http://10.0.0.134:8080/games")
        return response.body()
    }


    fun getGames() = flow {
        emit(getGamesApi())
    }

}