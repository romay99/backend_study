package dev.study.transaction_history.repository

import dev.study.transaction_history.entity.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionHistoryRepository : JpaRepository<TransactionHistory, Long> {
}
