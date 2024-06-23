package view

import client.NetworkClient
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.util.Duration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.serializer
import model.data.UnifiedGameData
import tornadofx.*

class LoadingView(
    id: Int,
    gameName: String,
    rating: String
) : View("Loading") {

    private val networkClient = NetworkClient()

    override val root = stackpane {
        progressindicator {
            maxWidth = 100.0
            maxHeight = 100.0
        }

    }

    //TODO load game from server
    init {
        GlobalScope.launch {
            //val unifiedGameData = networkClient.getGameByName(gameName)
            //serializeGameData(unifiedGameData)
        }


        runAsync {
            Thread.sleep(500)
        } ui {
            //replaceWith(GameView(id))
            replaceWith(GameView(gameName, rating))

        }
    }

    private fun serializeGameData(unifiedGameData: UnifiedGameData?) {
        if (unifiedGameData != null) {
            val initData = unifiedGameData.initData
            val mapData = unifiedGameData.mapData
            val mobsData = unifiedGameData.mobsData
            val towerData = unifiedGameData.towersData
            val wavesData = unifiedGameData.wavesData

            println("Game Name: ${initData.gameName}")
            println("City Health: ${initData.cityHealth}")
            println("Initial Money: ${initData.initialMoney}")

            println("Map Data: $mapData")
            println("Mobs Data: $mobsData")
            println("Tower Data: $towerData")
            println("Waves Data: ${wavesData.waves}")
        } else {
            println("Game not found or error fetching game.")
        }
    }

}
