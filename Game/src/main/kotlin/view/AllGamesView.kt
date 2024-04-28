package view

import javafx.geometry.Pos
import javafx.scene.image.Image
import tornadofx.*

class AllGamesView : View("") {

    override val root = stackpane {
        addClass("all-games-stack-pane")

        hbox {
            alignment = Pos.CENTER
            spacing = 40.0

            vbox{
                alignment = Pos.CENTER
                spacing = 40.0

                imageview(Image(resources.url("/configs/playButton.png").toString())) {
                    fitWidth = 100.0
                    fitHeight = 100.0
                    isPreserveRatio = true
                    setOnMouseClicked {
                        replaceWith(LoadingView::class)
                    }
                }
                imageview(Image(resources.url("/configs/playButton2.png").toString())) {
                    fitWidth = 100.0
                    fitHeight = 100.0
                    isPreserveRatio = true
                    setOnMouseClicked {
                        replaceWith(GameTwoMapsView::class)
                    }
                }
            }

            vbox{
                alignment = Pos.CENTER
                spacing = 40.0

                imageview(Image(resources.url("/configs/playButton.png").toString())) {
                    fitWidth = 100.0
                    fitHeight = 100.0
                    isPreserveRatio = true
                    setOnMouseClicked {
                        replaceWith(LoadingView::class)
                    }
                }
                imageview(Image(resources.url("/configs/playButton2.png").toString())) {
                    fitWidth = 100.0
                    fitHeight = 100.0
                    isPreserveRatio = true
                    setOnMouseClicked {
                        replaceWith(GameTwoMapsView::class)
                    }
                }
            }
        }
    }


}
