package viewModel

import model.RatingModel
import model.ScoreModel

class RatingController {
    private var rating = RatingModel()

    fun getRating(): List<ScoreModel> {
        return this.rating.getRating()
    }

    fun saveScore(score: ScoreModel) {
        this.rating.addScoreToRating(score)
        return
    }
}