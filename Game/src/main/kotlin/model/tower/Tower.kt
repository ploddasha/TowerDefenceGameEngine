package model.tower

import model.fromEditing.TowerType

interface Tower {
    var health: Int
    var fileName: String
    var damage: Int
    var range: Int
    var cost: Int
    var name: String
    var type: TowerType
}