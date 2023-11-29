package view

import model.MobsModel
import tornadofx.*
import java.io.File

class MenuView: View() {
    val mobsModel: MobsModel by inject()


    override val root = vbox (50) {
        label("Выберите с чего начать")
        hbox (10){
            button("Редактировать мобов") {
                action {
                    replaceWith(MobsEditingView::class)
                }
            }
            button("Редактировать башни") {
                action {
                    replaceWith(TowersEditingView::class)
                }
            }
            button("Редактировать карту")
            button("Редактировать режим")
            alignment = javafx.geometry.Pos.CENTER
        }
        button("Создать игру") {
            action {
                saveGameData()
            }
        }
        button("Вернуться") {
            action {
                replaceWith(MyView::class)
            }
        }
        alignment = javafx.geometry.Pos.CENTER

    }

    private fun saveGameData() {
        val file = File("Data.txt")
        file.writeText("Mobs Data:\n")
        mobsModel.mobsList.forEach { mob ->
            file.appendText("Cost: ${mob.cost}, Damage: ${mob.damage}, Health: ${mob.health}, Type: ${mob.type}\n")
        }

        println("Game data saved to Data.txt")
    }
}