package dev.study.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
open class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var name: String,
    open var amount: Int
)
