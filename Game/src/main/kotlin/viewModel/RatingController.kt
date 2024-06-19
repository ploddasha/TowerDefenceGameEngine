package viewModel

import javafx.beans.property.SimpleIntegerProperty
import model.RatingModel
import model.ScoreModel

class RatingController {
    private var rating = RatingModel()

    private val ratingProperty = SimpleIntegerProperty(rating.getRating())
    fun ratingProperty() = ratingProperty

    fun getRating(): Int{
        return ratingProperty.get()
    }

    fun saveScore(score: Int) {
        return ratingProperty.set(ratingProperty.get() + score)
    }
}