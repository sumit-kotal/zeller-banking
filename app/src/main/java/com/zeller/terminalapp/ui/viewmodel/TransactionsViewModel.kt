package com.zeller.terminalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transactions>>(emptyList())
    val transactions: StateFlow<List<Transactions>> get() = _transactions

    init {
        viewModelScope.launch {
            userRepository.getTransactions().collect { transactionList ->
                _transactions.value = transactionList
            }
        }
    }
}