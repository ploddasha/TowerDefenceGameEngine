package view

import javafx.geometry.Pos
import javafx.scene.layout.Border
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import viewModel.MusicController

class StartMenuView : View("Bashenki!") {

    private val musicController: MusicController by inject()
    init {
        importStylesheet("/styles/style.css")
        //musicController.playMusic("D:/ggwp/TowerDefenceGameEngine/Game/src/main/resources/music/start_music.mp3")
    }



    private val settingsView = SettingsView()

    override val root = stackpane {
        prefWidth = 900.0
        prefHeight = 600.0

        addClass("startMenu-stack-pane")

        vbox {
            hbox {
                alignment = Pos.TOP_RIGHT

                paddingRight = 20.0
                paddingTop = 10.0

                button("Settings") {
                    style {
                        fontSize = 14.px
                        padding = box(5.px, 10.px)
                        backgroundColor += Color.GRAY
                        textFill = Color.WHITE
                        fontWeight = FontWeight.BOLD
                    }
                    action {
                        settingsView.root.isVisible = true
                    }
                }
            }

            vbox {
                alignment = Pos.CENTER

                /*
                style {
                    backgroundColor += Color.rgb(0, 77, 64)
                } */

                label("Welcome to the Bashenki!") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 45.px
                        fontWeight = FontWeight.BOLD
                        textFill = Color.WHITE
                        strokeWidth = 2.px
                        stroke = Color.BLACK
                        paddingTop = 170.0
                        paddingBottom = 60.0
                    }
                }
                button("Play") {
                    style {
                        fontSize = 24.px
                        padding = box(10.px, 20.px)
                        backgroundColor += Color.rgb(255, 152, 0)
                        textFill = Color.WHITE
                        fontWeight = FontWeight.BOLD
                    }
                    action {
                        replaceWith(GameView::class)
                    }
                }
            }
        }

        // Добавляем SettingsView один раз
        add(settingsView)
        settingsView.root.isVisible = false
    }
}