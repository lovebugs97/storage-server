package com.lovebugs.storage_server.service

import com.lovebugs.storage_server.utils.S3Uploader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class StorageService(private val s3Uploader: S3Uploader) {
    fun uploadProfileImage(path: String, profileImage: MultipartFile): String {
        return s3Uploader.uploadFile(path, profileImage)
    }
}