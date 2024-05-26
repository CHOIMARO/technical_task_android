package com.choimaro.technical_task_android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.choimaro.domain.model.ImageModel

@Composable
fun ImageModelItem(imageModel: ImageModel, imageModifier: Modifier) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageModel.imageUrl),
            contentDescription = "",
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Text(text = "${imageModel.displaySiteName}")
        imageModel.datetime?.let { Text(text = it) }
    }
}
