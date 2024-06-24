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

class PlayerInfoView : View("Player Info") {
    private val networkClient = NetworkClient()
    private var players = mutableListOf<String>().asObservable()
    private var selectedPlayerInfo = SimpleStringProperty()
    private var selectedPlayerGames = SimpleStringProperty()
    private var selectedPlayerScores = SimpleStringProperty()

    override val root = hbox {
        spacing = 20.0

        vbox {
            spacing = 10.0
            alignment = Pos.CENTER_LEFT
            vgrow = Priority.ALWAYS

            listview(players) {
                selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                    if (newValue != null) {
                        loadPlayerInfo(newValue)
                        loadPlayerGames(newValue)
                        loadPlayerScores(newValue)
                    }
                }
            }

            button("Back to All Games") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    backgroundColor += Color.ORANGE
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                action {
                    replaceWith<AllGamesView>()
                }
            }
        }

        vbox{
            alignment = Pos.CENTER

            label("Player Information") {
                style {
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
            }
            textarea(selectedPlayerInfo) {
                isEditable = false
                vgrow = Priority.ALWAYS
            }
            label("Player Games history") {
                style {
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
            }
            textarea(selectedPlayerGames) {
                isEditable = false
                vgrow = Priority.ALWAYS
            }
            label("Player Scores") {
                style {
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
            }
            textarea(selectedPlayerScores) {
                isEditable = false
                vgrow = Priority.ALWAYS
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
