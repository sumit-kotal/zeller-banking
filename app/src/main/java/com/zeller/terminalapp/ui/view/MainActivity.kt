package com.zeller.terminalapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeller.terminalapp.R
import com.zeller.terminalapp.databinding.ActivityMainBinding
import com.zeller.terminalapp.ui.adapter.TransactionsAdapter
import com.zeller.terminalapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()

        binding.depositButton.setOnClickListener(this)
        binding.withdrawButton.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        transactionsAdapter = TransactionsAdapter()
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionsRecyclerView.adapter = transactionsAdapter
    }

    fun setupObservers() {
        lifecycleScope.launch {
            mainViewModel.userAccount.collect { userAccount ->
                binding.balance.text = userAccount.balance.toString()
            }
        }

        lifecycleScope.launch {
            mainViewModel.transactionStatus.collect { status ->
                if (status.isNotEmpty()) {
                    Toast.makeText(applicationContext, status, Toast.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.transactions.collect { transactions ->
                transactionsAdapter.submitList(transactions)
            }
        }
    }

    override fun onClick(view: View?) {
        val amountString = binding.amountInput.text.toString()
        if (amountString.isNotEmpty()) {
            val amount = amountString.toFloat()
            when (view?.id) {
                R.id.depositButton -> mainViewModel.deposit(amount)
                R.id.withdrawButton -> mainViewModel.withdraw(amount)
            }
        }
    }
}