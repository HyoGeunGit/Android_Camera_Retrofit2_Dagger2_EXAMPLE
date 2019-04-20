package org.niralmehta.camerauploadexample.modules.upload.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.coroutines.experimental.bg
import org.niralmehta.camerauploadexample.api.ImgurService
import org.niralmehta.camerauploadexample.datakt.DataStatus
import org.niralmehta.camerauploadexample.modules.upload.datakt.ImagePayload
import org.niralmehta.camerauploadexample.utils.api.Resource
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class UploadRepository @Inject constructor(private val retrofit: Retrofit) {

    private val imgurService by lazy {
        retrofit.create<ImgurService>(ImgurService::class.java)
    }

    fun uploadImage(clientId: String, image: ImagePayload): LiveData<Resource<DataStatus>> {
        val data = MutableLiveData<Resource<DataStatus>>()
        var resource: Resource<DataStatus>

        bg {
            try {
                val apiResponse = imgurService.postImageUpload(clientId, image).execute()

                resource = if (apiResponse.isSuccessful) {
                    Resource.success(apiResponse.body())
                } else {
                    Resource.error(apiResponse.message(), null)
                }

                data.postValue(resource)
            } catch (e: IOException) {
                resource = Resource.error("No network response", null)
                data.postValue(resource)
            } catch (e: RuntimeException) {
                resource = Resource.error("Something went wrong", null)
                data.postValue(resource)
            }
        }
        return data
    }

}