package model

class RatingModel {
    private var rating: List<ScoreModel> = listOf()

    fun getRating(): List<ScoreModel> {
        return this.rating
    }

    fun addScoreToRating(score: ScoreModel) {
        this.rating += score
    }
}