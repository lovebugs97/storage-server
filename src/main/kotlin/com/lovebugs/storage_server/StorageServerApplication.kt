package com.lovebugs.storage_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableDiscoveryClient
@ConfigurationPropertiesScan
@SpringBootApplication
class StorageServerApplication

fun main(args: Array<String>) {
	runApplication<StorageServerApplication>(*args)
}
