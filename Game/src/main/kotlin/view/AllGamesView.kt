package view

import client.NetworkClient
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kotlinx.coroutines.*
import model.data.Game
import tornadofx.*

class AllGamesView : View("") {
    private val networkClient = NetworkClient()
    private var games = emptyList<Game>()

    private val currView = this
    private lateinit var rating: String
    //private lateinit var ratingMenuView: RatingMenuView

    private val defaultGame = Game(id = 1, gameName = "Cool Game")


    override val root = stackpane {
        addClass("all-games-stack-pane")

        hbox {
            alignment = Pos.CENTER
            spacing = 40.0

            vbox{
                alignment = Pos.CENTER
                spacing = 40.0

                button("View Players") {
                    style {
                        fontSize = 14.px
                        padding = box(5.px, 10.px)
                        backgroundColor += Color.GREEN
                        textFill = Color.WHITE
                        fontWeight = FontWeight.BOLD
                    }
                    action {
                        replaceWith<PlayerInfoView>()
                    }
                }


                games.forEach { game ->
                    lateinit var currRating: String
                    lateinit var ratingMenuView: RatingMenuView
                    runBlocking {
                        rating = withContext(Dispatchers.IO) {
                            networkClient.getRating(game.gameName)
                        }
                        currRating = rating
                        println("gameName: " + game.gameName)
                        println("rating: " + currRating)
                        ratingMenuView = RatingMenuView(currView, game.gameName, currRating)
                    }

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
                            replaceWith(LoadingView(game.id, game.gameName, currRating))
                            println("gameName: " + game.gameName)
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
                            replaceWith(WaitingForConnectionView(game.gameName, currRating))
                        }
                    }

                    button("Rating") {
                        style {
                            fontSize = 14.px
                            padding = box(5.px, 40.px)
                            paddingAll = 5.0
                            backgroundColor += Color.RED
                            textFill = Color.WHITE
                            fontWeight = FontWeight.BOLD
                        }
                        vboxConstraints {
                            marginRight = 10.0
                        }
                        action {
                            replaceWith(ratingMenuView)
                        }
                    }

                        /*
                        GlobalScope.launch(Dispatchers.IO) {
                            rating = networkClient.getRating(game.gameName)
                            ratingMenuView = RatingMenuView(currView, game.gameName, rating)
                        }*/
                    }

            }
        }
    }

    init {
        loadGames()
    }


    private fun loadGames() {
        GlobalScope.launch {
            val result = runCatching {
                networkClient.getAllGames()
            }.getOrElse {
                emptyList()
            }

            runLater {
                games = if (result.isEmpty()) listOf(defaultGame) else result
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

            button("View Players") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    backgroundColor += Color.GREEN
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                action {
                    replaceWith<PlayerInfoView>()
                }
            }

                games.forEach { game ->
                    lateinit var currRating: String
                    lateinit var ratingMenuView: RatingMenuView
                    vbox {
                        alignment = Pos.CENTER
                        spacing = 40.0

                        runBlocking {
                            rating = withContext(Dispatchers.IO) {
                                networkClient.getRating(game.gameName)
                            }
                            currRating = rating
                            println("gameName: " + game.gameName)
                            println("rating: " + currRating)
                            ratingMenuView = RatingMenuView(currView, game.gameName, currRating)

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
                                    replaceWith(LoadingView(game.id, game.gameName, currRating))
                                    println("gameName: " + game.gameName)
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
                                    replaceWith(WaitingForConnectionView(game.gameName, currRating))
                                }
                            }

                            button("Rating") {
                                style {
                                    fontSize = 14.px
                                    padding = box(5.px, 40.px)
                                    paddingAll = 5.0
                                    backgroundColor += Color.RED
                                    textFill = Color.WHITE
                                    fontWeight = FontWeight.BOLD
                                }
                                vboxConstraints {
                                    marginRight = 10.0
                                }
                                action {
                                    replaceWith(ratingMenuView)
                                }
                            }
                        }
                        /*
                        GlobalScope.launch(Dispatchers.IO) {
                            rating = networkClient.getRating(game.gameName)
                            println("gameName: " + game.gameName)
                            println("rating: " + rating)
                            ratingMenuView = RatingMenuView(currView, game.gameName, rating)
                        }*/
                    }
                }
        }
    }

}
