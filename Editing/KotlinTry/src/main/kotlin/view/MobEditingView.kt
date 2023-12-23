package view

import model.MobModel
import model.MobType
import tornadofx.*

class MobEditingView: View() {

    val mobModel : MobModel by inject()
    val mobTypes = MobType.values().toList()


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