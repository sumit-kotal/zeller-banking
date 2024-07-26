package com.zeller.terminalapp.domain

import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val userAccount = UserAccount(balance = 0.0f)
    private val transactionsFlow = MutableStateFlow<List<Transactions>>(emptyList())

    override fun getUserAccount(): UserAccount {
        return userAccount
    }

    override fun deposit(amount: Float) {
        userAccount.balance += amount
        addTransaction(Transactions(isDeposit = true, amount = amount))
    }

    override fun withdraw(amount: Float): Boolean {
        return if (userAccount.balance >= amount) {
            userAccount.balance -= amount
            addTransaction(Transactions(isDeposit = false, amount = amount))
            true
        } else {
            false
        }
    }

    override fun getTransactions(): Flow<List<Transactions>> {
        return transactionsFlow
    }

    private fun addTransaction(transaction: Transactions) {
        val currentTransactions = transactionsFlow.value.toMutableList()
        currentTransactions.add(transaction)
        transactionsFlow.value = currentTransactions
    }
}