package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceApp()
        }
    }
}

@Composable
fun ArtSpaceApp() {

    val artworks = listOf(
        Artwork(R.drawable.art1, "Blooming Harmony", "Chinmayi", "2024"),
        Artwork(R.drawable.art2, "Canyon Depths", "Chinmayi", "2023"),
        Artwork(R.drawable.art3, "Winter Silence", "Chinmayi", "2022")
    )

    var index by remember { mutableStateOf(0) }

    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        ArtSpaceLandscape(artworks, index, { index = it })
    } else {
        ArtSpacePortrait(artworks, index, { index = it })
    }
}

@Composable
fun ArtSpacePortrait(
    artworks: List<Artwork>,
    index: Int,
    onIndexChange: (Int) -> Unit
) {

    val artwork = artworks[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ArtworkImage(artwork)

        Spacer(modifier = Modifier.height(20.dp))

        ArtworkDetails(artwork)

        Spacer(modifier = Modifier.height(20.dp))

        NavigationButtons(
            index,
            artworks.size,
            onIndexChange
        )
    }
}

@Composable
fun ArtSpaceLandscape(
    artworks: List<Artwork>,
    index: Int,
    onIndexChange: (Int) -> Unit
) {

    val artwork = artworks[index]

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ArtworkImage(
            artwork,
            Modifier.weight(1f)
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ArtworkDetails(artwork)

            Spacer(modifier = Modifier.height(20.dp))

            NavigationButtons(
                index,
                artworks.size,
                onIndexChange
            )
        }
    }
}

@Composable
fun ArtworkImage(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier.padding(10.dp)
    ) {

        Image(
            painter = painterResource(artwork.imageRes),
            contentDescription = artwork.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtworkDetails(artwork: Artwork) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = artwork.title,
            fontSize = 22.sp
        )

        Text(
            text = "${artwork.artist} (${artwork.year})",
            fontSize = 16.sp
        )
    }
}

@Composable
fun NavigationButtons(
    index: Int,
    total: Int,
    onIndexChange: (Int) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            onClick = {
                val newIndex = if (index > 0) index - 1 else total - 1
                onIndexChange(newIndex)
            }
        ) {
            Text("Previous")
        }

        Button(
            onClick = {
                val newIndex = (index + 1) % total
                onIndexChange(newIndex)
            }
        ) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtSpace() {
    ArtSpaceApp()
}