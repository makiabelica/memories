package com.example.memories_shared_images.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.memories_shared_images.R
import java.io.File

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para abrir la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess && imageUri != null) {
            navController.navigate("preview_screen/${Uri.encode(imageUri.toString())}")
        } else {
            Toast.makeText(context, "No se capturó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }

    // Lanzador para solicitar permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageFile(context)
            imageUri = uri
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen Circular Estática en la Parte Superio
        Image(
            painter = painterResource(id = R.drawable.photo), // Reemplaza con tu imagen
            contentDescription = "Imagen Estática",
            modifier = Modifier
                .size(320.dp)
                .clip(CircleShape) // Forma Circular
                .padding(8.dp),
            contentScale = ContentScale.Crop // Escalar imagen al círculo
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para tomar una foto
        StyledButton(
            text = "Tomar Foto",
            icon = Icons.Default.Favorite,
            onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para abrir la galería
        StyledButton(
            text = "Ver Galería",
            icon = Icons.Default.Star,
            onClick = { openGallery(context) }
        )
    }
}

@Composable
fun StyledButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFcdb4db), // Color Púrpura
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

fun createImageFile(context: Context): Uri {
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    val file = File(context.cacheDir, filename)
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}

fun openGallery(context: Context) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    context.startActivity(intent)
}