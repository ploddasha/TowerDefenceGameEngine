package model

class RatingModel {
    //private var rating: List<ScoreModel> = listOf()
    private var rating: Int = 0

    fun getRating(): Int {
        return this.rating
    }

    fun addToRating(score: Int) {
        this.rating += score
    }

    /*
    fun getRating(): List<ScoreModel> {
        return this.rating
    }

    fun addScoreToRating(score: ScoreModel) {
        this.rating += score
    } */
}