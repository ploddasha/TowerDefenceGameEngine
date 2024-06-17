package model.tower

class GroundTower(
    override var health: Int, override var fileName: String,
    override var damage: Int, override var range: Int,
    override var cost: Int, override var name: String
) : Tower {

}