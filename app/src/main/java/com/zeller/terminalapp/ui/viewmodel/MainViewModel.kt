package com.zeller.terminalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.data.model.UserAccount
import com.zeller.terminalapp.domain.UserRepository
import com.zeller.terminalapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userAccountChannel = Channel<UserAccount>(Channel.BUFFERED)
    val userAccount: Flow<UserAccount> = _userAccountChannel.receiveAsFlow()

    private val _transactionStatus = MutableStateFlow("")
    val transactionStatus: StateFlow<String> get() = _transactionStatus

    private val _transactions = MutableStateFlow<List<Transactions>>(emptyList())
    val transactions: StateFlow<List<Transactions>> get() = _transactions

    init {
        viewModelScope.launch {
            // Initialize user account state
            _userAccountChannel.send(userRepository.getUserAccount())

            // Collect transactions
            userRepository.getTransactions().collect { transactions ->
                _transactions.value = transactions
            }
        }
    }

    fun deposit(amount: Float) {
        viewModelScope.launch {
            userRepository.deposit(amount)
            _userAccountChannel.send(userRepository.getUserAccount())
            _transactionStatus.value = Constants.deposit_succesfull
        }
    }

    fun withdraw(amount: Float) {
        viewModelScope.launch {
            if (userRepository.getUserAccount().balance >= amount) {
                userRepository.withdraw(amount)
                _userAccountChannel.send(userRepository.getUserAccount())
                _transactionStatus.value = Constants.withdrawal_succesfull
            } else {
                _transactionStatus.value = Constants.not_enough_balance
            }
        }
    }
}