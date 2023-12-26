package viewModel

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import tornadofx.Controller
import java.io.File

class MusicController : Controller() {
    private var mediaPlayer: MediaPlayer? = null

    fun playMusic(filepath: String) {
        stopMusic()
        val musicFile = File(filepath)
        val media = Media(musicFile.toURI().toString())
        mediaPlayer = MediaPlayer(media)
        mediaPlayer?.play()
    }

    fun setVolume(volume: Double) {
        mediaPlayer?.volume = volume
    }

    fun stopMusic() {
        mediaPlayer?.stop()
    }
}