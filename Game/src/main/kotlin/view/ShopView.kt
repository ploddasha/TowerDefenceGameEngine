package view

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.CityModel
import model.tower.Tower
import tornadofx.*
import viewModel.*
import viewModel.towerControllers.FlyingTowerController
import viewModel.towerControllers.GroundTowerController

class ShopView(
    private val moneyController: MoneyController,
    private val groundTowerController: GroundTowerController,
    private val flyingTowerController: FlyingTowerController,
    private val cityModel: CityModel
) : View("Bashenki") {
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
                    if (groundTowerController.getPrice >= moneyController.getCurrentMoneyAmount()) {
                        val alert = Alert(Alert.AlertType.ERROR)
                        alert.title = "Ошибка"
                        alert.headerText = "Недостаточно средств!"
                        alert.contentText = "У вас недостаточно средств для покупки этой башни"
                        alert.showAndWait()
                    } else {
                        moneyController.writeOffMoney(groundTowerController.getPrice());
                        val tower = groundTowerController.createTower();

                        //+ добавить башню на экран
                    }
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
                action {
                    if (flyingTowerController.getPrice >= moneyController.getCurrentMoneyAmount()) {
                        val alert = Alert(Alert.AlertType.ERROR)
                        alert.title = "Ошибка"
                        alert.headerText = "Недостаточно средств!"
                        alert.contentText = "У вас недостаточно средств для покупки этой башни"
                        alert.showAndWait()
                    } else {
                        moneyController.writeOffMoney(flyingTowerController.getPrice());
                        val tower = flyingTowerController.createTower();

                        //+ добавить башню на экран
                    }
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
                    replaceWith(GameView())
                }
            }
        }


    }
}