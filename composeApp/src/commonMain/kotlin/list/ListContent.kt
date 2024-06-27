package list

import AppContent
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState


@Composable
fun ListContent(
    component: ListComponent
) {
    val games = component.model.subscribeAsState()

    AppContent(games) {
        component.onItemClicked(it)
    }

}