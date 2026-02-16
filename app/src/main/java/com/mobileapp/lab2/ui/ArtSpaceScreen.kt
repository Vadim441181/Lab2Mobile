package com.mobileapp.lab2.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mobileapp.lab2.R
import com.mobileapp.lab2.models.Artwork
import com.mobileapp.lab2.ui.theme.ArtworkBackgroundColor

fun getArtworks(): List<Artwork> = listOf(
    Artwork(R.drawable.img1, R.string.art1_title, R.string.art1_author),
    Artwork(R.drawable.img2, R.string.art2_title, R.string.art2_author),
    Artwork(R.drawable.img3, R.string.art3_title, R.string.art3_author)
)

@Composable
fun ArtSpaceScreen() {

    val artworks = getArtworks()
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_large)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ArtworkImage(
                    artwork = currentArtwork,
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight(0.9f)
                )

                ArtworkDescription(
                    artwork = currentArtwork,
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_large))
                )
            }

        } else {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ArtworkImage(
                    artwork = currentArtwork,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_medium)))

                ArtworkDescription(artwork = currentArtwork)
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_large))
        )

        ArtworkButtons(
            currentIndex = currentIndex,
            artworksSize = artworks.size,
            onPrevious = { currentIndex-- },
            onNext = { currentIndex++ }
        )
    }
}

@Composable
fun ArtworkImage(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = artwork.imageID),
        contentDescription = stringResource(artwork.titleID),
        modifier = modifier
    )
}

@Composable
fun ArtworkDescription(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(ArtworkBackgroundColor)
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(artwork.titleID),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_small))
        )

        Text(
            text = stringResource(artwork.authorID),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArtworkButtons(
    currentIndex: Int,
    artworksSize: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.button_spacing))
    ) {

        Button(
            onClick = onPrevious,
            modifier = Modifier.weight(1f),
            enabled = currentIndex > 0
        ) {
            Text(text = stringResource(R.string.previous))
        }

        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f),
            enabled = currentIndex < artworksSize - 1
        ) {
            Text(text = stringResource(R.string.next))
        }
    }
}
