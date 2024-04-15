package view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.geometry.Orientation
import javafx.scene.control.Slider
import tornadofx.*
import viewModel.MusicController

class SettingsView : View("Settings") {



    private val startMenuView: StartMenuView by inject()

    override val root = stackpane {
        rectangle {
            width = 500.0
            height = 350.0
            fill = Color.rgb(255, 152, 0)
            strokeWidth = 2.0
            stroke = Color.BLACK
        }

        vbox {
            alignment = Pos.CENTER

            vbox {
                alignment = Pos.TOP_CENTER
                label("Settings") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 25.px
                        fontWeight = FontWeight.BOLD
                        paddingBottom = 55.0
                        textFill = Color.WHITE
                    }
                }
            }


            label("*здесь будет настройка звука*") {
                style {
                    paddingTop = 35.0
                    paddingBottom = 55.0
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
                    //find(StartMenuView::class).openWindow()
                    replaceWith(StartMenuView())
                }
            }
        }
    }
}