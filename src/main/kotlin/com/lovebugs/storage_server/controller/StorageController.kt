package com.lovebugs.storage_server.controller

import com.lovebugs.storage_server.service.StorageService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/storage")
class StorageController(private val storageService: StorageService) {
    @PostMapping("/upload/profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadProfileImage(
        @RequestPart("path") path: String,
        @RequestPart("profileImage") profileImage: MultipartFile
    ): ResponseEntity<String> {
        val url = storageService.uploadProfileImage(path, profileImage)
        return ResponseEntity.ok(url)
    }
}