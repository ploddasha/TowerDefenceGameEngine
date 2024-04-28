package view

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.util.Duration
import tornadofx.*

class LoadingView : View("Loading") {
    override val root = stackpane {
        progressindicator {
            maxWidth = 100.0
            maxHeight = 100.0
        }

    }

    init {
        runAsync {
            Thread.sleep(2000)
        } ui {
            replaceWith(GameView::class)
        }
    }

}
