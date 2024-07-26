package com.zeller.terminalapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.zeller.terminalapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

}