package com.zeller.terminalapp.data.model

data class UserAccount(
    val balance: Float,
    val transactions: List<Transactions>
)