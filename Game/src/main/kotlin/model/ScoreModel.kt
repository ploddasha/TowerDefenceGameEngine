package model

class ScoreModel {
    private var score: Int = 0
    private var name: String = ""

    fun setName(name: String) {
        this.name = name
        return
    }

    fun setScore(score: Int) {
        this.score = score
        return
    }

    fun getName(): String {
        return this.name
    }

    fun getScore(): Int {
        return this.score
    }
}