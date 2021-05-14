package com.wavproductions.www.armitage

import com.wavproductions.viken.Viken
import com.wavproductions.viken.VikenImage
import com.wavproductions.www.armitage.Config.loadConfig
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val resources = Paths.get("resources").toAbsolutePath()
    if (Files.notExists(resources)) {
        Files.createDirectories(resources)
    }
    loadConfig()
    val conn = Metasploit()
    val debug = args.contains("debug-cmd") //testing metasploit connection and calls
    if (debug) {
        conn.load(debug = true)

    } else {
        val iconPath = Paths.get(resources.toString(), "icon.png")
        if (Files.notExists(iconPath)) {
            val stream: InputStream = Config.javaClass.getResourceAsStream("/icon.png")
                ?: throw RuntimeException("Corrupted jar resources!")
            Files.write(iconPath, stream.readAllBytes())
            stream.close()
        }
        Viken.init()
        val icon = VikenImage(iconPath)
        val window = Viken.createSync(800, 800, "Armitage ReWrite")
        requireNotNull(window) { "Could not create Window!" }
        window.setIconSync(icon.convertToIcon())
        window.showSync()
        Viken.loop()
        while (conn.consoleActive()) {
            Thread.onSpinWait()
        }
        Thread.sleep(20000) //run for 20 seconds for testing
        Viken.cleanup()
        icon.dispose()
    }

}