package view

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import model.MobModel
import model.MobType
import tornadofx.*
import viewModel.map.ImageManager
import java.io.File
import javax.imageio.ImageIO

class MobEditingView: View() {

    val mobModel : MobModel by inject()
    val mobTypes = MobType.values().toList()
    val imageManager = ImageManager()


    override val root = vbox {
        label("Редактирование моба")

        scrollpane {
            form {
                fieldset("Редактировать") {
                    field("Стоимость") {
                        textfield(mobModel.cost)
                    }
                    field("Урон") {
                        textfield(mobModel.damage)
                    }
                    field("Здоровье") {
                        textfield(mobModel.health)
                    }
                    field("Скорость") {
                        textfield(mobModel.speed)
                    }
                    field("Тип") {
                        combobox(mobModel.type, mobTypes)
                    }
                    //field("Может атаковать") {
                    //    checkbox("Может атаковать", mobModel.canAttack)
                    //}
                    field("Область атаки") {
                        textfield(mobModel.attackRange)
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
                                    alert(Alert.AlertType.INFORMATION, "Изображение не соответствует необходимым рамкам", "Нужно чтобы высота изображения была 256 пикселей, а ширина не превосходила это же значение.")
                                }
                                else {
                                    println("Selected file: ${selectedFile.absolutePath}")

                                    val fileName = selectedFile.name
                                    mobModel.fileName.value = fileName

                                    val destinationDirectory = File("./src/main/resources/mobsImages")
                                    imageManager.copyFileToProject(selectedFile, destinationDirectory, fileName)
                                }
                            } else {
                                println("File selection canceled.")
                            }

                        }
                    }

                    button("Сохранить") {
                        enableWhen(mobModel.dirty)
                        action {
                            save()
                        }
                    }
                    button("Сбросить").action {
                        mobModel.rollback()
                    }

                }
                button("Вернуться") {
                    action {
                        replaceWith(MobsEditingView::class)
                    }
                }
            }
        }



        alignment = javafx.geometry.Pos.CENTER
    }

    private fun save() {
        mobModel.commit()
    }

}