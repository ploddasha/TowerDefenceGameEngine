package model.tower

class FlyingTower(
    override var health: Int, override var fileName: String,
    override var damage: Int, override var range: Int,
    override var cost: Int, override var name: String
) : Tower {

}