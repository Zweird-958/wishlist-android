package com.example.wishlist_android.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun formatImageBody(image: File?): MultipartBody.Part? {
    val requestBody = image?.asRequestBody("image/*".toMediaTypeOrNull())
    val imagePart = requestBody?.let {
        MultipartBody.Part.createFormData(
            "image",
            image.name, it
        )
    }

    return imagePart
}