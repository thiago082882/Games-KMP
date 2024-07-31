package detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.seiko.imageloader.rememberImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    component: DetailComponent
) {

    val game = component.model.subscribeAsState()
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets(0.dp)),
                title = { Text("Games Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        component.onBackPressed()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp)
        ) {
            val painter = rememberImagePainter(url = game.value.item.imgUrl.toString())
            Image(
                painter,
                contentDescription = game.value.item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(width = 500.dp, height = 500.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Text("Games title: ${game.value.item.title}")
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Games Desc: ${game.value.item.shortDescription}",
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
    }
}
