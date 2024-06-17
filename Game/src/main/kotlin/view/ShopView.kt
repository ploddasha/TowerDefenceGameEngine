package view

import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.Alert
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.CityModel
import model.tower.Tower
import tornadofx.*
import viewModel.*
import viewModel.towerControllers.*

class ShopView(
    private val gameController: GameController,
    private val moneyController: MoneyController,
    private val cityController: CityController,
    private val groundTowerController: GroundTowerController,
    private val flyingTowerController: FlyingTowerController,
    private val cityModel: CityModel
) : View("Bashenki") {

    private var walkList = mutableListOf<Walk>()
    private var flyList = mutableListOf<Fly>()

    override val root = stackpane {
        addClass("shop")

        rectangle {
            width = 500.0
            height = 450.0
            fill = Color.rgb(100, 102, 100)
            strokeWidth = 2.0
            stroke = Color.BLACK
        }

        vbox {
            alignment = Pos.TOP_CENTER
            label("Shop") {
                styleClass.add("bordered");
                style {
                    fontSize = 25.px
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 55.0
                    textFill = Color.WHITE
                }
                vboxConstraints {
                    marginTop= 105.0
                }
            }
            /*
            button("Buy Ground Tower") {
                styleClass.add("bordered");
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
                    if (groundTowerController.getPrice() >= moneyController.getCurrentMoneyAmount()) {
                        val alert = Alert(Alert.AlertType.ERROR)
                        alert.title = "Ошибка"
                        alert.headerText = "Недостаточно средств!"
                        alert.contentText = "У вас недостаточно средств для покупки этой башни"
                        alert.showAndWait()
                    } else {
                        moneyController.writeOffMoney(groundTowerController.getPrice());
                        val tower = groundTowerController.createTower();
                        gameController.setTowerToPut(tower)
                    }
                }
            }*/
            button("Buy Ground Tower") {
                styleClass.add("bordered")
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

                // Создаем контекстное меню
                val contextMenu = ContextMenu()

                walkList = parseWalk()
                // Создаем элементы менюшки
                for (i in 0..walkList.lastIndex) {
                    val menuItem = MenuItem(walkList.get(i).Name)
                    menuItem.setOnAction {
                        if (walkList.get(i).Cost >= moneyController.getCurrentMoneyAmount()) {
                            val alert = Alert(Alert.AlertType.ERROR)
                            alert.title = "Ошибка"
                            alert.headerText = "Недостаточно средств!"
                            alert.contentText = "У вас недостаточно средств для покупки этой башни"
                            alert.showAndWait()
                        } else {
                            moneyController.writeOffMoney(walkList.get(i).Cost);

                            val health = walkList.get(i).Health
                            val fileName = walkList.get(i).FileName
                            val damage = walkList.get(i).Damage
                            val range = walkList.get(i).Range
                            val cost = walkList.get(i).Cost
                            val name = walkList.get(i).Name

                            val tower = groundTowerController.createTower(
                                health, fileName, damage, range, cost, name
                            );
                            gameController.setTowerToPut(tower)
                        }
                    }
                    contextMenu.items.add(menuItem)
                }
                action {
                    // Показываем контекстное меню при нажатии на кнопку
                    contextMenu.show(this@button, Side.BOTTOM, 0.0, 0.0)
                }
            }


            button("Buy Flying Tower") {
                styleClass.add("bordered");
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

                // Создаем контекстное меню
                val contextMenu = ContextMenu()

                flyList = parseFly()
                // Создаем элементы менюшки
                for (i in 0..flyList.lastIndex) {
                    val menuItem = MenuItem(flyList.get(i).Name)
                    menuItem.setOnAction {
                        if (flyList.get(i).Cost >= moneyController.getCurrentMoneyAmount()) {
                            val alert = Alert(Alert.AlertType.ERROR)
                            alert.title = "Ошибка"
                            alert.headerText = "Недостаточно средств!"
                            alert.contentText = "У вас недостаточно средств для покупки этой башни"
                            alert.showAndWait()
                        } else {
                            moneyController.writeOffMoney(flyList.get(i).Cost);

                            val health = flyList.get(i).Health
                            val fileName = flyList.get(i).FileName
                            val damage = flyList.get(i).Damage
                            val range = flyList.get(i).Range
                            val cost = flyList.get(i).Cost
                            val name = flyList.get(i).Name

                            val tower = flyingTowerController.createTower(
                                health, fileName, damage, range, cost, name
                            );
                            gameController.setTowerToPut(tower)
                        }
                    }
                    contextMenu.items.add(menuItem)
                }
                action {
                    // Показываем контекстное меню при нажатии на кнопку
                    contextMenu.show(this@button, Side.BOTTOM, 0.0, 0.0)
                }
            }

            button("HP for city") {
                styleClass.add("bordered");
                style {
                    fontSize = 18.px
                    padding = box(10.px, 20.px)
                    backgroundColor += Color.rgb(0, 0, 0)
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 80.0
                }
                action {
                    moneyController.writeOffMoney(cityModel.getCostOfHealthPoint());
                    cityModel.setHealth(cityModel.getHealth() + 1);
                    cityController.addCityHealth(1)
                }
            }

            button("Back") {
                styleClass.add("bordered");
                style {
                    fontSize = 18.px
                    padding = box(10.px, 20.px)
                    backgroundColor += Color.rgb(255, 152, 0)
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 10.0
                }
                action {
                    //replaceWith(GameView())
                    replaceWith(find<GameView>())
                }
            }
        }


    }
}