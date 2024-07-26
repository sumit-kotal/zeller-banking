package com.zeller.terminalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeller.terminalapp.data.model.UserAccount
import com.zeller.terminalapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userAccount = MutableStateFlow(userRepository.getUserAccount())
    val userAccount: StateFlow<UserAccount> get() = _userAccount

    fun deposit(amount: Float) {
        viewModelScope.launch {
            userRepository.deposit(amount)
            _userAccount.value = userRepository.getUserAccount()
        }
    }

    fun withdraw(amount: Float) {
        viewModelScope.launch {
            userRepository.withdraw(amount)
            _userAccount.value = userRepository.getUserAccount()
        }
    }
}