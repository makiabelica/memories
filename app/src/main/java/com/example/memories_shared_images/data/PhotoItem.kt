package com.example.memories_shared_images.data

import android.net.Uri

data class PhotoItem(
    val uri: Uri,
    val description: String = "Imagen Capturada",
    val location: String = "Ubicación desconocida"
)
