package view

import tornadofx.*
import javafx.geometry.Pos
import javafx.scene.paint.Color

class PauseMenuView : View("Pause Menu") {
    override val root = stackpane {
        // Контейнер для меню паузы
        vbox {
            alignment = Pos.CENTER
            style {
                backgroundColor += Color.rgb(0, 0, 0, 0.7)
                padding = box(20.px)
            }

            label("Pause Menu")
            button("Resume") {
                action {
                    removeFromParent()
                }
            }
        }
    }
}