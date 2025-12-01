package dev.study.entity.transactionHistory

import dev.study.entity.member.Member
import dev.study.domain.transactionHistory.TransactionHistoryType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_history")
open class TransactionHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    open var type: TransactionHistoryType = TransactionHistoryType.SUCCESS,

    open var amount: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    open var member: Member
)
