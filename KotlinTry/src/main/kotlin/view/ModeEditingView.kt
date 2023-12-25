package view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.scene.text.FontWeight
import model.Mob
import model.MobModel
import model.MobsModel
import model.mode.MobWithCount
import model.mode.ModeModel
import tornadofx.*

class ModeEditingView: View(){

    val mobModel: MobModel by inject()
    val mobsModel: MobsModel by inject()
    val modeModel: ModeModel by inject()

    val current = modeModel.selectedWave.value


    override val root = vbox(10) {
        label("Установите количество мобов для волны ${current}")

        button("Вернуться") {
            action {
                replaceWith(ModeChooseView::class)
            }
        }


        vbox(1) {
            for (i in 0 until modeModel.wavesLists[current].size) {
                form {
                    fieldset() {
                        hbox(1) {
                            field() {
                                textfield(modeModel.wavesLists[current].get(i).count.toString())
                            }
                            label("Тип: ${modeModel.wavesLists[current].get(i).mob.type}")
                            label("Скорость: ${modeModel.wavesLists[current].get(i).mob.speed}")
                            label("Цена: ${modeModel.wavesLists[current].get(i).mob.cost}")
                            label("Урон: ${modeModel.wavesLists[current].get(i).mob.damage}")
                            label("Здоровье: ${modeModel.wavesLists[current].get(i).mob.health}")

                        }

                    }
                }
            }
        }



        alignment = javafx.geometry.Pos.CENTER
    }
}