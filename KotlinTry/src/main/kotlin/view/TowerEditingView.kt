package view

import javafx.scene.control.Alert
import javafx.scene.control.DialogPane
import javax.imageio.ImageIO
import javafx.stage.FileChooser
import javafx.stage.StageStyle
import model.towers.*
import tornadofx.*
import viewModel.map.ImageManager
import java.io.File

class TowerEditingView: View() {

    val TowerModel : TowerModel by inject()
    val TowerTypes = TowerType.values().toList()
    val imageManager = ImageManager()
    val fileName = String ()

    override val root = vbox {
        label("Редактирование башни")
        scrollpane {
            form {
                fieldset("Редактировать") {
                    field("Имя башни") {
                        textfield(TowerModel.name)
                    }
                    field("Урон") {
                        textfield(TowerModel.damage)
                    }
                    field("Стоимость") {
                        textfield(TowerModel.cost)
                    }
                    field("Здоровье") {
                        textfield(TowerModel.health)
                    }
                    field("Радиус атаки") {
                        textfield(TowerModel.range)
                    }
                    field("Тип") {
                        combobox(TowerModel.type, TowerTypes)
                    }

                    button("Сбросить").action {
                        TowerModel.rollback()
                    }

                    button("Добавить изображение") {
                        action {
                            val fileChooser = FileChooser()
                            fileChooser.title = "Выберите изображение"
                            fileChooser.extensionFilters.addAll(
                                FileChooser.ExtensionFilter("Изображения", "*.png")
                            )

                            val selectedFile = fileChooser.showOpenDialog(currentWindow)
                            val image = ImageIO.read(selectedFile)
                            if (selectedFile != null) {
                                if (image.width > 256 || image.height != 256) {
                                    /*val alert = Alert(Alert.AlertType.INFORMATION)
                                    alert.initStyle(StageStyle.UTILITY)
                                    val dialogPane: DialogPane = alert.dialogPane
                                    dialogPane.stylesheets.add(javaClass.getResource("editorStyles.css").toExternalForm())
                                    dialogPane.styleClass.add("alert")
                                    alert.title = "Изображение не соответствует необходимым рамкам"
                                    alert.headerText = "Нужно чтобы высота изображения была 256 пикселей, а ширина не превосходила это же значение."
                                    alert.showAndWait()*/
                                    alert(Alert.AlertType.INFORMATION, "Изображение не соответствует необходимым рамкам", "Нужно чтобы высота изображения была 256 пикселей, а ширина не превосходила это же значение.")
                                }
                                else {
                                    println("Selected file: ${selectedFile.absolutePath}")

                                    val fileName = selectedFile.name
                                    TowerModel.fileName.value = fileName

                                    val destinationDirectory = File("./src/main/resources/towersImages")
                                    imageManager.copyFileToProject(selectedFile, destinationDirectory, fileName)
                                }
                            } else {
                                println("File selection canceled.")
                            }

                        }
                    }

                    button("Сохранить") {
                        enableWhen(TowerModel.dirty)
                        action {
                            save()
                        }
                    }
                }
                button("Вернуться") {
                    action {
                        replaceWith(TowersEditingView::class)
                    }
                }
            }
        }

        alignment = javafx.geometry.Pos.CENTER
    }

    private fun save() {
        TowerModel.commit()
    }

}