package viewModel

import javafx.beans.property.SimpleIntegerProperty
import model.WalletModel

class MoneyController {

    private var wallet = WalletModel()

    private val moneyAmountProperty = SimpleIntegerProperty(wallet.getMoneyAmount())
    fun moneyAmountProperty() = moneyAmountProperty

    private var moneySpent = 0

    fun getMoneySpent(): Int {
        return moneySpent
    }


    fun getCurrentMoneyAmount(): Int {
        return moneyAmountProperty.get()

    }

    fun addMoney(x: Int) {
        moneyAmountProperty.set(moneyAmountProperty.get() + x)

    }

    fun writeOffMoney(amount: Int) {
        moneyAmountProperty.set(moneyAmountProperty.get() - amount)
        moneySpent += amount
    }

    fun setMoney(moneyAmount: Int) {
        moneyAmountProperty.set(moneyAmount)
    }
}