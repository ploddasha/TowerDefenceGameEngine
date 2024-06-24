package view

import client.NetworkClient
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.scene.control.ListView
import kotlinx.coroutines.*
import tornadofx.*
import kotlin.math.round

class PlayerInfoView : View("Player Info") {
    private val networkClient = NetworkClient()
    private var players = mutableListOf<String>().asObservable()
    private var selectedPlayerInfo = SimpleStringProperty()
    private var selectedPlayerGames = SimpleStringProperty()
    private var selectedPlayerScores = SimpleStringProperty()

    override val root = vbox {
        spacing = 20.0
        addClass("players-info")

        hbox{
            alignment = Pos.CENTER
            paddingTop = 20.0
            vbox {
                alignment = Pos.CENTER
                paddingAll = 15.0

                label("Player Information") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 28.px
                        fontWeight = FontWeight.BOLD
                        paddingAll = 7.0
                        textFill = Color.WHITE
                        strokeWidth = 1.px
                        stroke = Color.BLACK
                    }
                }
                textarea(selectedPlayerInfo) {
                    addClass("textarea-font")
                    isEditable = false
                    vgrow = Priority.ALWAYS
                }


            }
            vbox {
                alignment = Pos.CENTER
                paddingAll = 15.0

                label("Player Games history") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 28.px
                        fontWeight = FontWeight.BOLD
                        paddingAll = 7.0
                        textFill = Color.WHITE
                        strokeWidth = 1.px
                        stroke = Color.BLACK
                    }
                }
                textarea(selectedPlayerGames) {
                    addClass("textarea-font")
                    isEditable = false
                    vgrow = Priority.ALWAYS
                }
            }
            vbox {
                alignment = Pos.CENTER
                paddingAll = 15.0

                label("Player Scores") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 28.px
                        fontWeight = FontWeight.BOLD
                        paddingAll = 7.0
                        textFill = Color.WHITE
                        strokeWidth = 1.px
                        stroke = Color.BLACK
                    }
                }
                textarea(selectedPlayerScores) {
                    addClass("textarea-font")
                    isEditable = false
                    vgrow = Priority.ALWAYS
                }
            }
        }

        hbox {
            spacing = 10.0
            alignment = Pos.CENTER
            vgrow = Priority.ALWAYS

            vbox {
                alignment = Pos.CENTER

                hbox {
                    alignment = Pos.CENTER
                    maxHeight = 200.0
                    maxWidth = 900.0
                    paddingBottom = 40.0

                    listview(players) {
                        addClass("listview-info")

                        selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                            if (newValue != null) {
                                loadPlayerInfo(newValue)
                                loadPlayerGames(newValue)
                                loadPlayerScores(newValue)
                            }
                        }
                    }
                }

                button("Back to All Games") {
                    style {
                        fontSize = 24.px
                        padding = box(5.px, 10.px)
                        backgroundColor += Color.BLACK
                        textFill = Color.WHITE
                        fontWeight = FontWeight.BOLD
                        paddingAll = 20.0
                    }
                    action {
                        replaceWith<AllGamesView>()
                    }
                }
            }
        }
    }

    init {
        loadPlayers()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPlayers() {
        GlobalScope.launch {
            val result = runCatching {
                networkClient.getAllPlayers()
            }.getOrElse {
                emptyList()
            }
            runLater {
                players.setAll(result)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPlayerInfo(player: String) {
        GlobalScope.launch {
            val result = runCatching {
                val trimmedPlayer = player.trim()
                networkClient.getPlayer(trimmedPlayer)
            }.getOrElse {
                "Failed to load player information"
            }
            runLater {
                selectedPlayerInfo.set(result)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPlayerGames(player: String) {
        GlobalScope.launch {
            val result = runCatching {
                val trimmedPlayer = player.trim()
                networkClient.getPlayerGames(trimmedPlayer)
            }.getOrElse {
                "Failed to load player games"
            }
            runLater {
                selectedPlayerGames.set(result)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPlayerScores(player: String) {
        GlobalScope.launch {
            val result = runCatching {
                val trimmedPlayer = player.trim()
                networkClient.getPlayerScores(trimmedPlayer)
            }.getOrElse {
                "Failed to load player scores"
            }
            runLater {
                selectedPlayerScores.set(result)
            }
        }
    }
}
