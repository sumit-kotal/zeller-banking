package com.zeller.terminalapp.data.model

data class Transactions(
    val id: Long = System.currentTimeMillis(), // Use current time as a unique ID
    val isDeposit: Boolean,
    val amount: Float
)