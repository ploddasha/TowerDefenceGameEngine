package view

import client.NetworkClient
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.geometry.Pos
import javafx.util.Duration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*

class WaitingForConnectionView(
    var name: String
) : View("Loading") {

    private val networkClient = NetworkClient()
    private val checkInterval = 5.0
    private val timeoutDuration = 60.0
    private var isTransitioned = false


    override val root = stackpane {
        vbox{
            spacing = 20.0
            alignment = Pos.CENTER

            progressindicator {
                maxWidth = 100.0
                maxHeight = 100.0
            }

            label("Wait for connection")
        }
    }

    //TODO load game from server
    init {

        val timeline = Timeline(KeyFrame(Duration.seconds(checkInterval), {
            checkConnection()
        }))
        timeline.cycleCount = Timeline.INDEFINITE
        timeline.play()

        runAsync {
            Thread.sleep((timeoutDuration * 1000).toLong())
        } ui {
            timeline.stop()
            replaceWith(AllGamesView())
        }
    }

    private fun checkConnection() {
        if (isTransitioned) return

        GlobalScope.launch {
            val result = networkClient.connect(name)
            if (!result) { //TODO delete ! !!!!!!!
                runLater {
                    if (!isTransitioned) {
                        isTransitioned = true
                        replaceWith(GameTwoMapsView())
                    }
                }
            }
        }
    }

}
