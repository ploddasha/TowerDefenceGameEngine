package viewModel

import javafx.beans.property.SimpleIntegerProperty
import model.WalletModel

class MoneyController {

    private var wallet = WalletModel()

    private val moneyAmountProperty = SimpleIntegerProperty(wallet.getMoneyAmount())
    fun moneyAmountProperty() = moneyAmountProperty


    fun getCurrentMoneyAmount(): Int {
        //return wallet.getMoneyAmount()
        return moneyAmountProperty.get()

    }

    fun addMoney(x: Int) {
        /*
        var amount = this.wallet.getMoneyAmount();
        amount += x
        this.wallet.setMoneyAmount(amount)
        return */
        moneyAmountProperty.set(moneyAmountProperty.get() + x)

    }

    fun writeOffMoney(amount: Int) {
        /*
        val currAmount = this.wallet.getMoneyAmount()
        this.wallet.setMoneyAmount(currAmount - amount)
        return */
        moneyAmountProperty.set(moneyAmountProperty.get() - amount)

    }
}