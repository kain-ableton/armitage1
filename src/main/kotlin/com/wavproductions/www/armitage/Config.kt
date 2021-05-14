package com.wavproductions.www.armitage

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object Config {
    private val config = Paths.get("resources", "config.bin")

    fun loadConfig() {
        if (Files.notExists(config)) {
            val default = ByteArray(1)
            default[0] = 0
            Files.write(config, default, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        }
        val data = Files.readAllBytes(config)
    }
}