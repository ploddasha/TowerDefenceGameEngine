package view

import tornadofx.*

class MyView : View("Hello") {

    override val root = borderpane {
        center {
            vbox(30) {
                label("Добро пожаловать!")
                button("Начать создавать игру") {
                    action {
                        replaceWith(MenuView::class)
                    }
                }
                alignment = javafx.geometry.Pos.CENTER
            }
        }

    }
}