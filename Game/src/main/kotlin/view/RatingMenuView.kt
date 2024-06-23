package view

import tornadofx.*
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight

class RatingMenuView(
    private val back_to_view: View,
    private val gameName: String,
    private var currRating: String
) : View("Rating Menu") {

    init {
        if (!currRating.equals("Empty now :(")) {
            reorganizeCurrRating(currRating)
        }
    }

    override val root = stackpane {

        addClass("rating")
        rectangle {
            width = 500.0
            height = 350.0
            fill = Color.rgb(255, 152, 0)
            strokeWidth = 2.0
            stroke = Color.BLACK
        }

        vbox {
            alignment = Pos.CENTER

            vbox {
                alignment = Pos.TOP_CENTER
                label("Rating") {
                    styleClass.add("bordered");
                    style {
                        fontSize = 25.px
                        fontWeight = FontWeight.BOLD
                        paddingBottom = 55.0
                        textFill = Color.WHITE
                    }
                }
            }

            vbox {
                alignment = Pos.CENTER
                label(currRating) {
                    style {
                        fontSize = 25.px
                        fontWeight = FontWeight.BOLD
                        paddingBottom = 55.0
                        textFill = Color.BLACK
                    }
                }
            }

            vbox {
                alignment = Pos.BOTTOM_CENTER

                button("Back") {
                    style {
                        fontSize = 18.px
                        padding = box(10.px, 20.px)
                        backgroundColor += Color.rgb(0, 0, 0)
                        textFill = Color.WHITE
                        fontWeight = FontWeight.BOLD
                    }
                    vboxConstraints {
                        marginBottom = 10.0
                    }
                    action {
                        replaceWith(back_to_view)
                    }
                }
            }
        }
    }

    private fun reorganizeCurrRating(rating: String) {
        rating.trimIndent()
        val lines = rating.split("\n")
        val result = StringBuilder()

        for ((index, line) in lines.withIndex()) {
            val parts = line.split(" ")
            val newLine = "${index + 1}) ${parts[0]}     ${parts[1]}"

            result.append(newLine).append("\n")
        }

        this.currRating = result.toString()
    }
}