package dev.study.movie.entity

import jakarta.persistence.*

@Entity
@Table(name = "movie")
open class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var name: String
)
