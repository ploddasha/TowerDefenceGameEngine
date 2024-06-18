package model

class WalletModel {
    private var moneyAmount: Int = 0

    fun getMoneyAmount(): Int {
        return this.moneyAmount
    }

    fun setMoneyAmount(x: Int) {
        this.moneyAmount = x
        return
    }
}