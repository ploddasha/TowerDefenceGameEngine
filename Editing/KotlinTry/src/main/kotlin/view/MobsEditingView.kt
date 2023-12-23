package view

import model.Mob
import model.MobModel
import model.MobType
import model.MobsModel
import tornadofx.*

class MobsEditingView: View() {

    val mobModel: MobModel by inject()
    val mobsModel: MobsModel by inject()


    override val root = vbox {
        spacing = 5.0

        label("Редактирование мобов")

        button("Добавить моба") {
            action {
                val newMob = Mob(0, 0, 0, 100, 0, MobType.Walk)
                mobsModel.addMob(newMob)
                mobModel.item = newMob
                replaceWith(MobEditingView::class)
            }
        }

        button("Вернуться") {
            action {
                replaceWith(MenuView::class)
            }
        }


        tableview (mobsModel.mobsList){
            title = "Mobs"
            column("Урон", Mob::damageProperty).contentWidth(padding = 50.0)
            column("Стоимость", Mob::costProperty).contentWidth(padding = 50.0)
            column("Здоровье", Mob::healthProperty).contentWidth(padding = 50.0)
            column("Скорость", Mob::speedProperty).contentWidth(padding = 50.0)
            column("Тип", Mob::typeProperty).contentWidth(padding = 50.0)
            //column("Атаковать", Mob::canAttack).contentWidth(padding = 50.0)
            column("Область атаки", Mob::attackRange).contentWidth(padding = 50.0)
            bindSelected(mobModel)

            contextmenu {
                item("Редактировать").action {
                    selectedItem?.apply {
                        replaceWith(MobEditingView::class)
                    }
                }
                item("Удалить").action {
                    selectedItem?.apply {
                        mobsModel.mobsList.remove(mobModel.item)
                    }
                }
            }

            columnResizePolicy = SmartResize.POLICY

        }

        alignment = javafx.geometry.Pos.CENTER
    }
}