package list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.models.Games
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewmodel.HomeViewModel

interface ListComponent {

    val model : Value<Model>

    fun onItemClicked(item:Games)

    data class  Model(
        val items : List<Games>
    )
}

class DefaultListComponent(
    private val componentContext :  ComponentContext,
    private val homeViewModel: HomeViewModel,
    private  val onItemSelected : (item:Games)-> Unit

):ListComponent,ComponentContext by componentContext{

private val _model = MutableValue<ListComponent.Model>(ListComponent.Model(items = emptyList()))
    override val model: Value<ListComponent.Model> = _model
    override fun onItemClicked(item: Games) {
       onItemSelected(item)
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            homeViewModel.games.collect{
                _model.value = ListComponent.Model(items = it)
            }
        }

    }
}