package view

import model.towers.*
import tornadofx.*

class TowerEditingView: View() {

    val TowerModel : TowerModel by inject()
    val TowerTypes = TowerType.values().toList()


    override val root = vbox {
        label("Редактирование башни")

        form {
            fieldset("Редактировать") {
                field("Имя башни"){
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
                field("Тип") {
                    combobox(TowerModel.type, TowerTypes)
                }

                button("Сбросить").action {
                    TowerModel.rollback()
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

        alignment = javafx.geometry.Pos.CENTER
    }

    private fun save() {
        TowerModel.commit()
    }

}