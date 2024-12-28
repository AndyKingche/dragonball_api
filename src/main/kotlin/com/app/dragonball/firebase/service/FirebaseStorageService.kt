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

@Service
class FirebaseStorageService {
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

    private fun getExtension(fileName: String): String = fileName.substring(fileName.lastIndexOf("."))

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
