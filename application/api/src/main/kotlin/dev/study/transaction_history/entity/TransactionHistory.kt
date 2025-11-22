package dev.study.transaction_history.entity

import dev.study.member.entity.Member
import dev.study.transaction_history.domain.TransactionHistoryType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_history")
open class TransactionHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,

    open var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    open var type: TransactionHistoryType = TransactionHistoryType.SUCCESS,

    open var amount: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    open var member: Member
)
