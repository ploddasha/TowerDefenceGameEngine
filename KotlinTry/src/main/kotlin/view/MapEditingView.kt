package view

import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.FontWeight
import javafx.stage.FileChooser
import model.map.MapModel
import tornadofx.*
import java.io.File
import viewModel.map.ImageManager
import viewModel.map.MapCheck


class MapEditingView: View() {

    private val mapModel: MapModel by inject()


    private val grassBackgroundImage = Image(resources.url("/grass.jpg").toString())
    private val grassImage = Image(resources.url("/map/grass.png").toString())
    //private val babbleImage = Image(resources.url("/map/babble.jpg").toString())

    val imageManager = ImageManager()


    private val tiles = listOf(
        "/map/grass.png",
        "/map/sand.png",
        "/map/water.png",
        "/map/city.jpg"
    )

    private var selectedTile: String? = null
    private val tileMap = mutableMapOf<Pair<Int, Int>, String>()

    private val errorLabel = label {
        style {
            fontWeight = FontWeight.THIN
            fontSize = 10.px
        }
    }

    override val root = borderpane() {
        top() {
            vbox(1) {
                label("Редактирование карты") {
                    style {
                        fontWeight = FontWeight.THIN
                        fontSize = 16.px

                    }
                }

                hbox(5) {
                    button("Добавить изображение") {
                        action {
                            val fileChooser = FileChooser()
                            fileChooser.title = "Выберите изображение"
                            fileChooser.extensionFilters.addAll(
                                FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg"),
                                FileChooser.ExtensionFilter("Все файлы", "*.*")
                            )

                            val selectedFile = fileChooser.showOpenDialog(currentWindow)

                            if (selectedFile != null) {
                                println("Selected file: ${selectedFile.absolutePath}")

                                val newFileName = "MapImage.png"
                                val destinationDirectory = File("./src/main/resources")
                                imageManager.copyFileToProject(selectedFile, destinationDirectory, newFileName)

                            } else {
                                println("File selection canceled.")
                            }

                        }
                    }

                    button("Сохранить изменения") {
                        action {
                            saveTileInformation()
                        }
                    }

                    button("Вернуться") {
                        action {
                            replaceWith(MenuView::class)
                        }
                    }
                    alignment = javafx.geometry.Pos.CENTER
                }

                alignment = javafx.geometry.Pos.CENTER
            }
        }

        left() {
            tilepane {
                hgap = 10.0
                vgap = 10.0
                padding = Insets(0.0, 1.0, 0.0, 40.0)

                tiles.forEach { imagePath ->

                    val imageView = ImageView(Image(javaClass.getResourceAsStream(imagePath)))
                    imageView.isPreserveRatio = true
                    imageView.fitWidth = 50.0
                    imageView.fitHeight = 50.0

                    imageView.setOnMouseClicked {
                        selectedTile = imagePath
                        println("Tile selected: $selectedTile")
                    }

                    children.add(imageView)
                }
                alignment = javafx.geometry.Pos.CENTER

            }

        }

        center {
            stackpane {
                imageview(grassBackgroundImage) {
                    fitHeight = 340.0
                    fitWidth = 450.0
                }
                gridpane {
                    hgap = 1.0
                    vgap = 1.0
                    paddingAll = 0.0

                    repeat(30) { row ->
                        repeat(40) { col ->
                            val cellImageView = ImageView(grassImage)
                            cellImageView.isPreserveRatio = true
                            cellImageView.fitWidth = 10.0
                            cellImageView.fitHeight = 10.0
                            cellImageView.opacity = 0.6
                            tileMap[Pair(row, col)] = "/map/grass.png"


                            cellImageView.setOnMouseClicked {
                                handleTileClick(cellImageView, col, row)
                            }

                            add(cellImageView, col, row)
                        }
                    }
                    alignment = javafx.geometry.Pos.CENTER

                }
            }
        }

        bottom {
            hbox(10) {
                add(errorLabel)

                alignment = javafx.geometry.Pos.CENTER
            }
        }

    }



    private fun handleTileClick(cellImageView: ImageView, col: Int, row: Int) {
        if (selectedTile != null) {
            cellImageView.image = Image(javaClass.getResourceAsStream(selectedTile!!))
            cellImageView.opacity = 0.5
            tileMap[Pair(row, col)] = selectedTile!!
            println("Tile changed at position ($col, $row) to $selectedTile")
        }
    }

    private fun saveTileInformation() {
        val mapCheck = MapCheck()
        val check = mapCheck.checkSandConnectivity(tileMap)
        val cityCheck = mapCheck.checkCity(tileMap)
        val connectedCheck = mapCheck.checkCityConnectedToRoad(tileMap)


        if (!check) {
            errorLabel.text = "Дорога должна быть связной!"
        } else {
            if (!cityCheck) {
                errorLabel.text = "Город должен быть один!"
            } else {
                if (!connectedCheck) {
                    errorLabel.text = "Город должен быть соединен с дорогой!"
                } else {
                    println("Saving tile information")
                    mapModel.saveInformation(tileMap)
                    errorLabel.text = "Изменения сохранены"
                }
            }
        }
    }

}