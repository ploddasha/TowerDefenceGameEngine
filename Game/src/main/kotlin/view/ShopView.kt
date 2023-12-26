package view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class ShopView : View("Bashenki") {
    override val root = stackpane {
        addClass("shop")

        vbox {
            alignment = Pos.TOP_CENTER
            label("Welcome to the towers shop!") {
                style {
                    fontSize = 25.px
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 55.0
                    textFill = Color.WHITE
                }
            }
        }

        button("Back") {
            style {
                fontSize = 18.px
                padding = box(10.px, 20.px)
                backgroundColor += Color.rgb(0, 0, 0)
                textFill = Color.WHITE
                fontWeight = FontWeight.BOLD
            }
            action {
                replaceWith(GameView())
            }
        }
    }
}