package org.niralmehta.camerauploadexample.modules.upload.datakt

import com.squareup.moshi.Json

data class ImagePayload(
    @field:Json(name = "image")
    @Json(name = "image")
    val image: String,
    @field:Json(name = "title")
    @Json(name = "title")
    val title: String?,
    @field:Json(name = "name")
    @Json(name = "name")
    val name: String?,
    @field:Json(name = "description")
    @Json(name = "description")
    val description: String?
)