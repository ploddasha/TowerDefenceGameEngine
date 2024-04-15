package viewModel

import model.WalletModel

class MoneyController {
    private var wallet = WalletModel()

    fun getCurrentMoneyAmount(): Int {
        return wallet.getMoneyAmount()
    }

    fun addMoney(x: Int) {
        var amount = this.wallet.getMoneyAmount();
        amount += x
        this.wallet.setMoneyAmount(amount)
        return
    }

    fun writeOffMoney(amount: Int) {
        val currAmount = this.wallet.getMoneyAmount()
        this.wallet.setMoneyAmount(currAmount - amount)
        return
    }
}