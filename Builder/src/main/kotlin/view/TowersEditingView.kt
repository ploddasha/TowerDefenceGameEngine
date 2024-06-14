package view

import model.Mob
import model.towers.*
import tornadofx.*

class TowersEditingView: View() {

    val TowerModel: TowerModel by inject()
    val TowersModel: TowersModel by inject()


    override val root = vbox {
        spacing = 5.0

        label("Редактирование башен")

        button("Добавить башню") {
            action {
                val newTower = Tower("имя", "fileName.png", 0, 0, 0, TowerType.Walk, 0)
                TowersModel.addTower(newTower)
                TowerModel.item = newTower
                replaceWith(TowerEditingView::class)
            }
        }

        button("Вернуться") {
            action {
                replaceWith(MenuView::class)
            }
        }


        tableview (TowersModel.TowersList){
            title = "Towers"
            column("Имя", Tower::nameProperty).contentWidth(padding = 50.0)
            column("Урон", Tower::damageProperty).contentWidth(padding = 50.0)
            column("Стоимость", Tower::costProperty).contentWidth(padding = 50.0)
            column("Здоровье", Tower::healthProperty).contentWidth(padding = 50.0)
            column("Радиус атаки", Tower::rangeProperty).contentWidth(padding = 50.0)
            column("Тип", Tower::typeProperty).contentWidth(padding = 50.0)
            column("Имя файла", Tower::fileNameProperty).contentWidth(padding = 50.0)
            bindSelected(TowerModel)

            contextmenu {
                item("Редактировать").action {
                    selectedItem?.apply {
                        replaceWith(TowerEditingView::class)
                    }
                }
                item("Удалить").action {
                    selectedItem?.apply {
                        TowersModel.TowersList.remove(TowerModel.item)
                    }
                }
            }

            columnResizePolicy = SmartResize.POLICY
        }


        alignment = javafx.geometry.Pos.CENTER
    }
}