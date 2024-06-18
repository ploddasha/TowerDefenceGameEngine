package view

import client.NetworkClient
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.geometry.Pos
import javafx.util.Duration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*

class WaitingForConnectionView(
    //id: Int
) : View("Loading") {

    private val networkClient = NetworkClient()

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

        GlobalScope.launch {
            val result = networkClient.connect()
            if (result) {
                runLater {
                    replaceWith(GameTwoMapsView())
                }
            } else {
                Thread.sleep(500)
                runLater {
                    replaceWith(AllGamesView())
                }
            }

        }
    }

}
