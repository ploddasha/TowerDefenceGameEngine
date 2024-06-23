package view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import tornadofx.*

class SaveRatingView : View("Save Your Rating") {
    private val nameProperty = SimpleStringProperty()

    var onSave: (String) -> Unit = {}

    override val root = form {
        fieldset("Enter your name to save your rating") {
            field("Name") {
                textfield(nameProperty)
            }
        }
        button("Save") {
            action {
                if (nameProperty.value.isNotBlank()) {
                    onSave(nameProperty.value)
                    close()
                } else {
                    alert(Alert.AlertType.WARNING, "Invalid Input", "Name cannot be blank.")
                }
            }
        }
    }
}
