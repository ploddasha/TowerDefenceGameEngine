package view

import client.NetworkClient
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.util.Duration
import tornadofx.*

class LoadingView(
    id: Int,
    gameName: String,
    ratingMenuView: RatingMenuView
) : View("Loading") {

    private val networkClient = NetworkClient()

    override val root = stackpane {
        progressindicator {
            maxWidth = 100.0
            maxHeight = 100.0
        }

    }

    //TODO load game from server
    init {
        runAsync {
            Thread.sleep(500)
        } ui {
            //replaceWith(GameView(id))
            replaceWith(GameView(gameName, ratingMenuView))

        }
    }

}
