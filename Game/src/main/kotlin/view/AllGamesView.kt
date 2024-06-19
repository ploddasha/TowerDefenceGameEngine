package view

import client.NetworkClient
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import model.data.Game
import tornadofx.*

class AllGamesView : View("") {
    private val networkClient = NetworkClient()
    private var games = emptyList<Game>()


    override val root = stackpane {
        addClass("all-games-stack-pane")

        hbox {
            alignment = Pos.CENTER
            spacing = 40.0

            vbox{
                alignment = Pos.CENTER
                spacing = 40.0

                    games.forEach { game ->
                        button("Start Game ${game.gameName}") {
                            style {
                                fontSize = 14.px
                                padding = box(5.px, 10.px)
                                paddingAll = 5.0
                                backgroundColor += Color.ORANGE
                                textFill = Color.WHITE
                                fontWeight = FontWeight.BOLD
                            }
                            vboxConstraints {
                                marginRight = 10.0
                            }
                            action {
                                replaceWith(LoadingView(game.id))
                            }
                        }

                        button("Play together") {
                            style {
                                fontSize = 14.px
                                padding = box(5.px, 10.px)
                                paddingAll = 5.0
                                backgroundColor += Color.BLUEVIOLET
                                textFill = Color.WHITE
                                fontWeight = FontWeight.BOLD
                            }
                            vboxConstraints {
                                marginRight = 10.0
                            }
                            action {
                                replaceWith(WaitingForConnectionView())
                            }
                        }
                    }

            }
        }
    }

    init {
        loadGames()
    }


    private fun loadGames() {
        GlobalScope.launch {
            val result = networkClient.getAllGames()
            runLater {
                games = result
                updateUI()
                saveGamesToJSON()
            }
        }
    }

    private fun saveGamesToJSON() {
    }

    private fun updateUI() {
        root.children.clear()
        hbox {
            alignment = Pos.CENTER
            spacing = 40.0

                games.forEach { game ->
                    vbox {
                        alignment = Pos.CENTER
                        spacing = 40.0
                        button("Start Game ${game.gameName}") {
                            style {
                                fontSize = 14.px
                                padding = box(5.px, 10.px)
                                paddingAll = 5.0
                                backgroundColor += Color.ORANGE
                                textFill = Color.WHITE
                                fontWeight = FontWeight.BOLD
                            }
                            vboxConstraints {
                                marginRight = 10.0
                            }
                            action {
                                replaceWith(LoadingView(game.id))
                            }
                        }

                        button("Play together") {
                            style {
                                fontSize = 14.px
                                padding = box(5.px, 10.px)
                                paddingAll = 5.0
                                backgroundColor += Color.BLUEVIOLET
                                textFill = Color.WHITE
                                fontWeight = FontWeight.BOLD
                            }
                            vboxConstraints {
                                marginRight = 10.0
                            }
                            action {
                                replaceWith(WaitingForConnectionView())
                            }
                        }
                    }
                }
        }
    }

}
