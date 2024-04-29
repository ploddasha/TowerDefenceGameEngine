package model

class WalletModel {
    private var moneyAmount: Int = 1000 //TODO

    fun getMoneyAmount(): Int {
        return this.moneyAmount
    }

    fun setMoneyAmount(x: Int) {
        this.moneyAmount = x
        return
    }
}