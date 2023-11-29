package view

import model.towers.Tower
import model.towers.TowerModel
import model.towers.TowerType
import model.towers.TowersModel
import tornadofx.*

class TowersEditingView: View() {

    val TowerModel: TowerModel by inject()
    val TowersModel: TowersModel by inject()


    override val root = vbox {
        spacing = 5.0

        label("Редактирование башен")

        button("Добавить башню") {
            action {
                val newTower = Tower(0, 0, 0, TowerType.Walk, 0)
                TowersModel.addTower(newTower)
                TowerModel.item = newTower
                replaceWith(TowerEditingView::class)
            }
        }
        button("Редактировать") {
            action {
                replaceWith(TowerEditingView::class)
            }
        }
        button("Удалить") {
            action {
                TowersModel.TowersList.remove(TowerModel.item)
            }
        }

        button("Вернуться") {
            action {
                replaceWith(MenuView::class)
            }
        }


        tableview (TowersModel.TowersList){
            title = "Towers"
            column("Урон", Tower::damageProperty).contentWidth(padding = 50.0)
            column("Стоимость", Tower::costProperty).contentWidth(padding = 50.0)
            column("Здоровье", Tower::healthProperty).contentWidth(padding = 50.0)
            column("Тип", Tower::typeProperty).contentWidth(padding = 50.0)
            column("Радиус атаки", Tower::rangeProperty).contentWidth(padding = 50.0)
            bindSelected(TowerModel)
            columnResizePolicy = SmartResize.POLICY

        }

        alignment = javafx.geometry.Pos.CENTER
    }
}