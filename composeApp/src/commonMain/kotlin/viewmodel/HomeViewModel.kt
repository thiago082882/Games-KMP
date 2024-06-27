package viewmodel

import data.models.Games
import repository.HomeRepository
import data.models.GamesItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel  : ViewModel(){

    private  val _games = MutableStateFlow<List<Games>>(listOf())
    val games = _games.asStateFlow()

    private val homeRepository = HomeRepository()


    init {
        viewModelScope.launch {
            homeRepository.getGames().collect { games ->
                _games.update { it + games }
            }
        }
    }
}