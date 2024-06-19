package view

import tornadofx.*
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.geometry.Orientation
import javafx.scene.control.Slider
import viewModel.MusicController

class PauseMenuView : View("Pause Menu") {

    /*
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
*/


    private val gameView: GameView by inject()



    override val root = stackpane {

        addClass("pause")
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
                label("Pause Menu") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 25.px
                        fontWeight = FontWeight.BOLD
                        paddingBottom = 55.0
                        textFill = Color.WHITE
                    }
                }
            }
            /*
            label("*здесь будет настройка звука*") {
                style {
                    paddingTop = 35.0
                    paddingBottom = 55.0
                }
            }*/



            button("Back") {
                style {
                    fontSize = 18.px
                    padding = box(10.px, 20.px)
                    backgroundColor += Color.rgb(0, 0, 0)
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 10.0
                }
                action {
                    replaceWith(GameView())
                }
            }

            button("Start Menu") {
                style {
                    fontSize = 18.px
                    padding = box(10.px, 20.px)
                    backgroundColor += Color.RED
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                    startMargin = 10.px
                }
                action {
                    replaceWith(StartMenuView())
                }
            }
        }
    }
}