package viewModel

class VictoryController  {
    private var finished = false

    private var gameOver = false
    private var cityHealth = 0
    private var rating = 0

    private var enemyGameOver = false
    private var enemyCityHealth = 0
    private var enemyRating = 0

    private fun setFinished() {
        this.finished = true
    }

    fun getFinished(): Boolean {
        return finished
    }

    fun setEnemyGameOver(b: Boolean) {
        this.enemyGameOver = b
        if (gameOver) {
            setFinished()
        }
    }

    fun setEnemyCityHealth(cityHealth: Int) {
        this.enemyCityHealth = cityHealth
    }

    fun setEnemyRating(rating: Int) {
        this.enemyRating = rating
    }

    fun setGameOver(b: Boolean) {
        this.gameOver = b
        if (enemyGameOver) {
            setFinished()
        }
    }

    fun setCityHealth(cityHealth: Int) {
        this.cityHealth = cityHealth
    }

    fun setRating(rating: Int) {
        this.rating = rating
    }

    fun check(): String {
        var result = "nothing"
        if (gameOver && enemyGameOver) {
            setFinished()

            result = "draw"
            if ((cityHealth > 0 && enemyCityHealth > 0) || (cityHealth <= 0 && enemyCityHealth <= 0)) {
                if (enemyRating > rating) {
                    return "lose"
                } else if (enemyRating < rating) {
                    return "victory"
                }
            } else if (cityHealth > 0) {
                return "victory"
            } else if (cityHealth < 0) {
                return "lose"
            }
        }
        return result
    }


}