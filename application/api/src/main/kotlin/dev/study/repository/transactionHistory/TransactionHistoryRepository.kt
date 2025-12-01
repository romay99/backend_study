package dev.study.repository.transactionHistory

import dev.study.entity.transactionHistory.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionHistoryRepository : JpaRepository<TransactionHistory, Long> {
}
