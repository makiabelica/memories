package com.example.memories_shared_images.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.memories_shared_images.data.PhotoItem
import java.io.InputStream

@Composable
fun PreviewScreen(navController: NavController, imageUri: String?) {
    val context = LocalContext.current

    if (imageUri == null) {
        Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        return
    }

    val uri = Uri.parse(imageUri)
    val bitmap = loadBitmapFromUri(context, uri)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        bitmap?.let {
            PhotoCard(
                photoItem = PhotoItem(
                    uri = uri,
                    description = "Imagen Capturada",
                    location = "Ubicación desconocida"
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        StyledButton(
            text = "Compartir Imagen",
            icon = Icons.Default.Share,
            onClick = { shareImage(context, uri) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        StyledButton(
            text = "Tomar otra Foto",
            icon = Icons.Default.Favorite,
            onClick = { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        StyledButton(
            text = "Volver al Menú Principal",
            icon = Icons.Default.ArrowBack,
            onClick = {
                navController.navigate("camera_screen") {
                    popUpTo("camera_screen") { inclusive = true }
                }
            }
        )
    }
}


@Composable
fun PhotoCard(photoItem: PhotoItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(model = photoItem.uri),
                contentDescription = "Imagen Capturada ☀️",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Text(
                text = photoItem.description,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun loadBitmapFromUri(context: Context, uri: Uri): ImageBitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun shareImage(context: Context, uri: Uri) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/jpeg"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartir imagen usando"))
}