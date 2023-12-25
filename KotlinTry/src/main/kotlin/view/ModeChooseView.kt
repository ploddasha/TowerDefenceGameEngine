package view

import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import model.MobsModel
import model.mode.ModeModel
import tornadofx.*

class ModeChooseView: View(){

    val modeModel : ModeModel by inject()
    val selectedWave = SimpleIntegerProperty()
    val mobsModel: MobsModel by inject()


    override val root = vbox(10) {
        label("Редактирование режима")
            form {
                fieldset() {
                    checkbox("Режим волн", modeModel.wavesMode)

                    field("Количество волн") {
                        textfield(modeModel.countOfWaves)
                    }

                    button("Сохранить выбор") {
                        action {
                            modeModel.commit()
                            //modeModel.selectedWave.value = selectedWave.value
                            save()
                        }
                    }
                    alignment = javafx.geometry.Pos.CENTER
                }
            }


        val waves = FXCollections.observableArrayList((1..modeModel.countOfWaves.value).toList())
        combobox(modeModel.selectedWave, waves)

        modeModel.countOfWaves.onChange {
            waves.setAll((1..it!!).toList())
        }


        button("Выбрать") {
            action {
                replaceWith(ModeEditingView::class)
            }
        }

        button("Вернуться") {
            action {
                replaceWith(MenuView::class)
            }
        }
        alignment = javafx.geometry.Pos.CENTER
    }

    fun save() {
        modeModel.saveInformation(mobsModel.mobsList)
    }
}