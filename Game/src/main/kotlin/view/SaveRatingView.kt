package view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*

class SaveRatingView : View("Game Results") {
    private val nameProperty = SimpleStringProperty()
    private val resultMessageProperty = SimpleStringProperty()

    var onSave: (String) -> Unit = {}

    override val root = vbox(20) {
        paddingAll = 20
        alignment = Pos.CENTER
        prefWidth = 400.0
        prefHeight = 300.0

        label(resultMessageProperty) {
            style {
                fontSize = 20.px
                wrapText = true
            }
            maxWidth = 360.0
            alignment = Pos.CENTER
        }
        form {
            maxWidth = 360.0
            fieldset("Enter your name to save your rating") {
                field("Name") {
                    textfield(nameProperty) {
                        maxWidth = 340.0
                    }
                }
            }
        }
        button("Save") {
            maxWidth = 100.0
            action {
                if (nameProperty.value.isNotBlank()) {
                    onSave(nameProperty.value)
                    close()
                } else {
                    resultMessageProperty.set("Name cannot be blank.")
                }
            }
        }
    }

    fun setResultMessage(message: String) {
        resultMessageProperty.set(message)
    }
}
