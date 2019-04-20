package org.niralmehta.camerauploadexample.api

import org.niralmehta.camerauploadexample.datakt.DataStatus
import org.niralmehta.camerauploadexample.modules.upload.datakt.ImagePayload
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Imgur API Endpoints
 */
interface ImgurService {

    @POST("3/image")
    fun postImageUpload(
        @Header("Authorization") clientId: String,
        @Body imagePayload: ImagePayload
    ): Call<DataStatus>
}