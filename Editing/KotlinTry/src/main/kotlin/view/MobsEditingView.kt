package view

import model.Mob
import model.MobModel
import model.MobType
import model.MobsModel
import tornadofx.*

/*
    должно быть отображение всех созданных мобов
    с кнопкой создать уровень
    и возможностью редактировать уровень
 */

class MobsEditingView: View() {

    val mobModel: MobModel by inject()
    val mobsModel: MobsModel by inject()


    override val root = vbox {
        spacing = 5.0

        label("Редактирование мобов")

        button("Добавить моба") {
            action {
                val newMob = Mob(0, 0, 0, MobType.Walk)
                mobsModel.addMob(newMob)
                mobModel.item = newMob
                replaceWith(MobEditingView::class)
            }
        }
        button("Редактировать") {
            action {
                replaceWith(MobEditingView::class)
            }
        }
        button("Удалить") {
            action {
                mobsModel.mobsList.remove(mobModel.item)
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
            column("Тип", Mob::typeProperty).contentWidth(padding = 50.0)
            bindSelected(mobModel)
            columnResizePolicy = SmartResize.POLICY

        }

        alignment = javafx.geometry.Pos.CENTER
    }
}