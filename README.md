Este proyecto es una aplicación de Android desarrollada en Kotlin usando Jetpack Compose, con funcionalidades principales que incluyen:

- Tomar fotos usando la cámara del dispositivo.
- Guardar las fotos y visualizarlas en una galería.
- Compartir fotos capturadas con otras aplicaciones.
- Interfaz moderna usando Material Design 3.

La aplicación está diseñada para ejecutarse en dispositivos con Android SDK 16 (Android 4.1 Jelly Bean) o superior, y es compatible con las versiones más recientes (incluyendo Android 14 Upside Down Cake).


### Instrucciones para Ejecutar el Proyecto

**Paso 1: Clonar o Descargar el Proyecto**
Clona el repositorio o descarga los archivos del proyecto en tu máquina local.

```
git clone https://github.com/makiabelica/memories
```

**Paso 2: Seleccionar Android Upside Down Cake**

1. Abre Android Studio.
2. Selecciona "Open Project" y navega a la carpeta donde clonaste o descargaste el proyecto.
3. Asegúrate de que tu Android Studio esté configurado para utilizar la API 34 (Android Upside Down Cake).


**Paso 4: Ejecutar el Proyecto**

1. Haz clic en el botón Run en Android Studio o usa el atajo Shift + F10.
2. Elige tu dispositivo emulado o físico.
3. Espera a que la aplicación se instale y abra.

### Permisos Requeridos


**Cámara:** Necesario para tomar fotos con el dispositivo.

```
<uses-permission android:name="android.permission.CAMERA" />

```

Para versiones de Android 13 (API 33) y superiores, se debe usar el permiso `READ_MEDIA_IMAGES` para **acceder a las imágenes.**

```
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

```

Necesario para leer imágenes en versiones más antiguas de Android.

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

```
**Permiso para Compartir URI:**

Permite **compartir archivos con otras aplicaciones mediante un FileProvider.**

```
<uses-permission android:name="android.permission.GRANT_READ_URI_PERMISSION" />
```

Al iniciar la aplicación, se solicitan los permisos de cámara y acceso a la galería cuando el usuario interactúa con las funcionalidades correspondientes.
La aplicación usa ``ActivityResultContracts.RequestPermission`` para gestionar los permisos en tiempo de ejecución.

### Funcionalidades Principales
- **Tomar Fotos:** Permite capturar fotos utilizando la cámara del dispositivo.
Guardar y Compartir Imágenes:

- Las **fotos tomadas se guardan localmente** y pueden **compartirse con otras aplicaciones** (como WhatsApp o Email).
- Galería: Se incluye la funcionalidad para **abrir la galería del dispositivo** y seleccionar imágenes
