package viewmodel

import data.models.Games
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.HomeRepository

class HomeViewModel(
    private val homeRepository : HomeRepository
): ViewModel(){

    private  val _games = MutableStateFlow<List<Games>>(listOf())
    val games = _games.asStateFlow()


    init {
        viewModelScope.launch {
            homeRepository.getGames().collect { games ->
                _games.update { it + games }
            }
        }
    }
}