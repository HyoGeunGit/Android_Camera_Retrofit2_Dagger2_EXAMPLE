package org.niralmehta.camerauploadexample.modules.upload.viewmodel

import androidx.lifecycle.ViewModel
import android.graphics.Bitmap
import android.util.Base64
import org.niralmehta.camerauploadexample.modules.upload.datakt.ImagePayload
import org.niralmehta.camerauploadexample.modules.upload.repository.UploadRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class UploadViewModel @Inject constructor(private val uploadRepository: UploadRepository) :
    ViewModel() {

    fun get64BaseImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun uploadImage(clientId: String, image: ImagePayload) =
        uploadRepository.uploadImage(clientId, image)

}