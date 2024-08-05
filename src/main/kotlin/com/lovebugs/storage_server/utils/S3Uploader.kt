package com.lovebugs.storage_server.utils

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@Component
class S3Uploader(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) {
    companion object {
        private val logger = LoggerFactory.getLogger(S3Uploader::class.java)
    }

    fun uploadFile(path: String, multipartFile: MultipartFile): String {
        val uploadFile = convert(multipartFile)
        val url = putS3(path, uploadFile)
        removeNewFile(uploadFile)
        return url
    }

    private fun putS3(path: String, uploadFile: File): String {
        val pubObjectRequest = PutObjectRequest(bucket, path, uploadFile)
            .withCannedAcl(CannedAccessControlList.PublicRead)

        amazonS3.putObject(pubObjectRequest)

        return amazonS3.getUrl(bucket, path).toString()
    }

    private fun removeNewFile(uploadFile: File) {
        if (uploadFile.delete()) {
            logger.info("Deleted ${uploadFile.name}")
        } else {
            logger.warn("Failed to delete ${uploadFile.name}")
        }
    }

    private fun convert(multipartFile: MultipartFile): File {
        logger.info("Convert MultipartFile ${multipartFile.originalFilename} to File")

        multipartFile.originalFilename?.let {
            val convertFile = File(it)

            if (convertFile.createNewFile()) {
                val fos = FileOutputStream(convertFile)

                try {
                    fos.write(multipartFile.bytes)
                } catch (e: Exception) {
                    logger.error(e.message)
                } finally {
                    fos.close()
                }
            }

            return convertFile
        }

        throw IllegalArgumentException("MultipartFile does not exist")
    }
}