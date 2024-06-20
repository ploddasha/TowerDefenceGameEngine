package viewModel

import javafx.beans.property.SimpleIntegerProperty
import model.WalletModel

class MoneyController {

    private var wallet = WalletModel()

    private val moneyAmountProperty = SimpleIntegerProperty(wallet.getMoneyAmount())
    fun moneyAmountProperty() = moneyAmountProperty


    fun getCurrentMoneyAmount(): Int {
        return moneyAmountProperty.get()

    }

    fun addMoney(x: Int) {
        moneyAmountProperty.set(moneyAmountProperty.get() + x)

    }

    fun writeOffMoney(amount: Int) {
        moneyAmountProperty.set(moneyAmountProperty.get() - amount)

    }

    fun setMoney(moneyAmount: Int) {
        moneyAmountProperty.set(moneyAmount)
    }
}