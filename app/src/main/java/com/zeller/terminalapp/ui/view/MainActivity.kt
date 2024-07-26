package com.zeller.terminalapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zeller.terminalapp.R
import com.zeller.terminalapp.databinding.ActivityMainBinding
import com.zeller.terminalapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.depositButton.setOnClickListener(this)
        binding.withdrawButton.setOnClickListener(this)
        setContentView(binding.root)

        // Observe changes in user account
        lifecycleScope.launch {
            mainViewModel.userAccount.collect { userAccount ->
                binding.balance.text = userAccount.balance.toString()
            }
        }
    }

    override fun onClick(view: View?) {
        val amt = binding.amountInput.text.toString().toFloatOrNull() ?: return
        when (view?.id) {
            R.id.withdrawButton -> mainViewModel.withdraw(amt)
            R.id.depositButton -> mainViewModel.deposit(amt)
        }
    }
}