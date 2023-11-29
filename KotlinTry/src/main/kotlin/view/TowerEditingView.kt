package view

import model.towers.TowerModel
import model.towers.TowerType
import tornadofx.*

class TowerEditingView: View() {

    val TowerModel : TowerModel by inject()
    val TowerTypes = TowerType.values().toList()


    override val root = vbox {
        label("Редактирование башню")

        form {
            fieldset("Редактировать") {
                field("Стоимость") {
                    textfield(TowerModel.cost)
                }
                field("Урон") {
                    textfield(TowerModel.damage)
                }
                field("Здоровье") {
                    textfield(TowerModel.health)
                }
                field("Тип") {
                    combobox(TowerModel.type, TowerTypes)
                }
                field("Радиус атаки") {
                    textfield(TowerModel.range)
                }
                button("Сохранить") {
                    enableWhen(TowerModel.dirty)
                    action {
                        save()
                    }
                }
                button("Сбросить").action {
                    TowerModel.rollback()
                }

            }
            button("Вернуться") {
                action {
                    replaceWith(TowersEditingView::class)
                }
            }
        }

        alignment = javafx.geometry.Pos.CENTER
    }

    private fun save() {
        TowerModel.commit()
    }

}