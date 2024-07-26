package com.zeller.terminalapp.domain.repository

import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {

    private val _userAccount = MutableStateFlow(UserAccount(balance = 0f, transactions = emptyList()))
    val userAccount: Flow<UserAccount> get() = _userAccount

    fun getUserAccount(): UserAccount = _userAccount.value

    suspend fun deposit(amount: Float) {
        val current = _userAccount.value
        val updatedAccount = current.copy(
            balance = current.balance + amount,
            transactions = current.transactions + Transactions(isDeposit = true, amount = amount)
        )
        _userAccount.value = updatedAccount
    }

    suspend fun withdraw(amount: Float) {
        val current = _userAccount.value
        if (current.balance >= amount) {
            val updatedAccount = current.copy(
                balance = current.balance - amount,
                transactions = current.transactions + Transactions(isDeposit = false, amount = amount)
            )
            _userAccount.value = updatedAccount
        }
    }
}