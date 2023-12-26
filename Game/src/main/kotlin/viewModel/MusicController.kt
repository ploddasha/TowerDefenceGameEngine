package viewModel

import tornadofx.*
import javazoom.jl.decoder.JavaLayerException
import javazoom.jl.player.Player
import java.io.FileInputStream
import java.io.IOException
import java.util.concurrent.Executors

class MusicController : Controller() {
    private var player: Player? = null
    //private val volumeProperty = SimpleDoubleProperty(0.5)
    private val volumeProperty = 0.5

    //var volume: Double by volumeProperty

    var volume = 0.5

    private val executor = Executors.newSingleThreadExecutor()

    fun playMusic(filePath: String, loop: Boolean = true) {
        stopMusic()

        try {
            val fis = FileInputStream(filePath)
            player = Player(fis)
            executor.execute {
                try {
                    player?.play()
                } catch (e: JavaLayerException) {
                    e.printStackTrace()
                }
                if (loop) {
                    playMusic(filePath, loop)
                }
            }

        } catch (e: JavaLayerException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopMusic() {
        player?.close()
    }

    fun changeMusic(filePath: String) {
        stopMusic()
        playMusic(filePath)
    }
/*
    fun setVolume(value: Double) {
        volume = value
        player?.let { player ->
            val gainControl = player.gainControl
            gainControl.level = value.toFloat()
        }
    }*/
}