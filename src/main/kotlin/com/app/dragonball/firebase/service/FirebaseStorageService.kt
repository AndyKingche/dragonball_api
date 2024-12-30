@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.firebase.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Servicio para gestionar la carga y eliminación de archivos en Firebase Storage.
 *
 * Esta clase proporciona métodos para cargar archivos al almacenamiento de Firebase y obtener
 * un enlace de descarga, así como eliminar archivos previamente subidos. Utiliza las credenciales
 * de un archivo de configuración de Firebase para autenticar las solicitudes.
 */
@Service
class FirebaseStorageService {
    /**
     * Carga un archivo en Firebase Storage.
     *
     * Este método recibe un archivo y su nombre, luego lo sube a Firebase Storage en el bucket
     * especificado, y devuelve la URL de descarga pública del archivo.
     *
     * @param file El archivo que se va a cargar.
     * @param fileName El nombre del archivo que se cargará en Firebase Storage.
     * @return La URL de descarga del archivo subido.
     */
    private fun uploadFile(
        file: File,
        fileName: String,
    ): String {
        val blobId = BlobId.of("importadorakaleth.appspot.com", "$fileName")
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build()
        val inputStream: InputStream? = FirebaseStorageService::class.java.classLoader.getResourceAsStream("izenshy-projects-firebase.json")
        val credentials: GoogleCredentials? = GoogleCredentials.fromStream(inputStream)
        val storage: Storage =
            StorageOptions
                .newBuilder()
                .setCredentials(credentials)
                .build()
                .service
        storage.create(blobInfo, file.readBytes())

        val downloadUrl = "https://firebasestorage.googleapis.com/v0/b/importadorakaleth.appspot.com/o/%s?alt=media"
        return String.format(downloadUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8))
    }

    /**
     * Convierte un archivo MultipartFile a un archivo físico en el sistema.
     *
     * Este método toma un archivo `MultipartFile` y lo convierte en un archivo físico que se
     * puede subir a Firebase Storage.
     *
     * @param multipartFile El archivo recibido como MultipartFile.
     * @param fileName El nombre que se asignará al archivo convertido.
     * @return El archivo convertido a un archivo físico.
     */
    private fun convertToFile(
        multipartFile: MultipartFile,
        fileName: String,
    ): File {
        val tempFile = File(fileName)
        FileOutputStream(tempFile).use { fos ->
            fos.write(multipartFile.bytes)
        }
        return tempFile
    }

    /**
     * Obtiene la extensión de un archivo.
     *
     * Este método obtiene la extensión de un archivo basado en su nombre.
     *
     * @param fileName El nombre del archivo del cual obtener la extensión.
     * @return La extensión del archivo (por ejemplo, `.jpg`, `.png`).
     */
    private fun getExtension(fileName: String): String = fileName.substring(fileName.lastIndexOf("."))

    /**
     * Carga un archivo a Firebase Storage y devuelve la URL de descarga.
     *
     * Este método maneja la carga de un archivo `MultipartFile` y genera un nombre único para el archivo
     * antes de subirlo a Firebase Storage. Devuelve la URL del archivo cargado.
     *
     * @param multipartFile El archivo que se desea cargar.
     * @return La URL de descarga pública del archivo cargado.
     */
    fun upload(multipartFile: MultipartFile): String {
        return try {
            var fileName = multipartFile.originalFilename ?: return "Image couldn't upload, Something went wrong"
            fileName = UUID.randomUUID().toString().plus(this.getExtension(fileName))

            val file = this.convertToFile(multipartFile, fileName)
            val url = this.uploadFile(file, fileName)
            file.delete()
            url
        } catch (e: Exception) {
            e.printStackTrace()
            "Image couldn't upload, Something went wrong"
        }
    }

    /**
     * Elimina un archivo de Firebase Storage.
     *
     * Este método recibe el nombre de un archivo y lo elimina de Firebase Storage.
     *
     * @param fileName El nombre del archivo a eliminar.
     * @return Un mensaje indicando el resultado de la operación de eliminación.
     */
    fun deleteFile(fileName: String): String {
        return try {
            val blobId = BlobId.of("importadorakaleth.appspot.com", "api-dragonball/$fileName")
            val inputStream: InputStream? =
                FirebaseStorageService::class.java.classLoader.getResourceAsStream(
                    @Suppress("ktlint:standard:max-line-length")
                    "izenshy-projects-firebase.json",
                )
            val credentials: GoogleCredentials? = GoogleCredentials.fromStream(inputStream)
            val storage: Storage =
                StorageOptions
                    .newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .service

            val blob = storage.delete(blobId)
            return "File deleted successfully."
        } catch (e: Exception) {
            e.printStackTrace()
            "An error occurred while deleting the file."
        }
    }
}
