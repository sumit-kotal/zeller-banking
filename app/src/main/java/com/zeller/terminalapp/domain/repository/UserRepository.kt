package com.zeller.terminalapp.domain

import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserAccount(): UserAccount
    fun deposit(amount: Float)
    fun withdraw(amount: Float): Boolean
    fun getTransactions(): Flow<List<Transactions>>
}