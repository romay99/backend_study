import dev.study.domain.seat.SeatStatus
import dev.study.entity.movie.Movie
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "seat")
open class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var col: String,
    open var num: Int,
    @Enumerated(EnumType.STRING)
    open var status: SeatStatus = SeatStatus.AVAILABLE,

    @ManyToOne(fetch = FetchType.LAZY)
    open var movie: Movie
)