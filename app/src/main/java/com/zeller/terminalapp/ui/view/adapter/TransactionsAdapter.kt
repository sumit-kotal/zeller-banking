package com.zeller.terminalapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.zeller.terminalapp.data.model.Transactions
import com.zeller.terminalapp.databinding.ItemTransactionBinding

class TransactionsAdapter : ListAdapter<Transactions, TransactionsViewHolder>(TransactionsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }
}

class TransactionsViewHolder(private val binding: ItemTransactionBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun bind(transaction: Transactions) {
        binding.transaction = transaction
        binding.executePendingBindings()
    }
}

class TransactionsDiffCallback : DiffUtil.ItemCallback<Transactions>() {
    override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem == newItem
    }
}