package com.zeller.terminalapp.ui.view

import com.zeller.terminalapp.data.model.Transactions

class TransactionsList {
    private val transactionsList: MutableList<Transactions> = mutableListOf()
    fun addTransaction(transactions: Transactions) {
        transactionsList.add(transactions)
    }
}